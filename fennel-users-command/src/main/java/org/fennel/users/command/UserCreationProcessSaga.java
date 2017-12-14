package org.fennel.users.command;

import java.time.Duration;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.SagaLifecycle;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.ScheduleToken;
import org.axonframework.spring.stereotype.Saga;
import org.fennel.services.User;
import org.fennel.services.UserCheck;
import org.fennel.users.api.user.CreateCommand;
import org.fennel.users.api.usercreationprocess.ConfirmEvent;
import org.fennel.users.api.usercreationprocess.ConfirmedCommand;
import org.fennel.users.api.usercreationprocess.CreatedEvent;
import org.fennel.users.api.usercreationprocess.FailCheckCommand;
import org.fennel.users.api.usercreationprocess.NewPinEvent;
import org.fennel.users.api.usercreationprocess.TerminatedEvent;
import org.fennel.users.api.usercreationprocess.DataCheckCommand;
import org.springframework.beans.factory.annotation.Autowired;

@Saga(sagaStore = "sagaRepository")
public class UserCreationProcessSaga {

  @Autowired
  private transient CommandGateway commandGateway;
  @Autowired
  private transient EventScheduler eventScheduler;
  @Autowired
  private transient UserCheck      userCheck;

  private ScheduleToken terminationToken;
  private String        pin;
  private String        displayName;
  private String        username;
  private String        password;

  @StartSaga
  @SagaEventHandler(associationProperty = "processId")
  public void handle(final CreatedEvent event) {
    pin = event.getPin();
    displayName = event.getDisplayName();
    username = event.getUsername();
    password = event.getPassword();

    userCheck.check(User.builder()
      .displayName(displayName)
      .username(username)
      .password(password)
      .processId(event.getProcessId())
      .build())
      .thenAccept(r -> {
        if (r) {
          commandGateway.send(DataCheckCommand.builder()
            .processId(event.getProcessId())
            .build());
          terminationToken = eventScheduler.schedule(Duration.ofDays(30), TerminatedEvent.builder()
            .processId(event.getProcessId())
            .build());
        } else {
          commandGateway.send(FailCheckCommand.builder()
            .processId(event.getProcessId())
            .build());
        }
      });
  }

  @SagaEventHandler(associationProperty = "processId")
  public void handle(final ConfirmEvent event) {
    if (pin != null && pin.equals(event.getPin())) {

      commandGateway.sendAndWait(ConfirmedCommand.builder()
        .processId(event.getProcessId())
        .build());

      commandGateway.sendAndWait(CreateCommand.builder()
        .userId(UUID.randomUUID().toString())
        .displayName(displayName)
        .username(username)
        .password(password)
        .processId(event.getProcessId())
        .build());

      if (terminationToken != null) {
        eventScheduler.cancelSchedule(terminationToken);
      }
      SagaLifecycle.end();
    }
  }

  @SagaEventHandler(associationProperty = "processId")
  public void handle(final NewPinEvent event) {
    pin = event.getPin();
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "processId")
  public void handle(final TerminatedEvent event) {

  }
}
