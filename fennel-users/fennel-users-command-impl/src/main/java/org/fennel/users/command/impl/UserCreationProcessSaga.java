package org.fennel.users.command.impl;

import java.time.Duration;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.ScheduleToken;
import org.axonframework.spring.stereotype.Saga;
import org.fennel.common.services.IdGeneratorService;
import org.fennel.common.services.User;
import org.fennel.common.services.UserCheck;
import org.fennel.common.util.UserData;
import org.fennel.users.command.user.CreateCommand;
import org.fennel.users.command.user.UserType;
import org.fennel.users.command.usercreationprocess.ConfirmEvent;
import org.fennel.users.command.usercreationprocess.ConfirmedCommand;
import org.fennel.users.command.usercreationprocess.ConfirmedEvent;
import org.fennel.users.command.usercreationprocess.CreatedEvent;
import org.fennel.users.command.usercreationprocess.DataCheckCommand;
import org.fennel.users.command.usercreationprocess.FailCheckCommand;
import org.fennel.users.command.usercreationprocess.NewPinEvent;
import org.fennel.users.command.usercreationprocess.TerminatedEvent;
import org.springframework.beans.factory.annotation.Autowired;

@Saga(sagaStore = "sagaRepository")
public class UserCreationProcessSaga {

  @Autowired
  private transient CommandGateway     commandGateway;
  @Autowired
  private transient EventScheduler     eventScheduler;
  @Autowired
  private transient UserCheck          userCheck;
  @Autowired
  private transient IdGeneratorService idGenerator;

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
        if (event.isConfirmed()) {
          commandGateway.sendAndWait(ConfirmedCommand.builder()
            .processId(event.getProcessId())
            .build());
        } else {
          terminationToken = eventScheduler.schedule(Duration.ofDays(30), TerminatedEvent.builder()
            .processId(event.getProcessId())
            .build());
        }
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

      if (terminationToken != null) {
        eventScheduler.cancelSchedule(terminationToken);
      }
    }
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "processId")
  public void handle(final ConfirmedEvent event) {
    commandGateway.sendAndWait(CreateCommand.builder()
      .userId(idGenerator.generate("user"))
      .displayName(displayName)
      .username(username)
      .password(password)
      .processId(event.getProcessId())
      .type(UserType.NORMAL)
      .userData(UserData.builder() // TODO FIX ME
        .userId("1234")
        .build())
      .build());
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
