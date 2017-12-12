package org.fennel.users.sagas;

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
import org.fennel.users.api.commands.ConfirmedUserCommand;
import org.fennel.users.api.commands.CreateUserCommand;
import org.fennel.users.api.events.ConfirmUserEvent;
import org.fennel.users.api.events.NewUserPinEvent;
import org.fennel.users.api.events.UserCreationProcessCreated;
import org.fennel.users.api.events.UserCreationProcessTerminated;
import org.springframework.beans.factory.annotation.Autowired;

@Saga(sagaStore = "sagaRepository")
public class UserCreationProcessSaga {

  @Autowired
  private transient CommandGateway commandGateway;
  @Autowired
  private transient EventScheduler eventScheduler;

  private ScheduleToken terminationToken;
  private String        pin;
  private String        displayName;
  private String        username;
  private String        password;

  @StartSaga
  @SagaEventHandler(associationProperty = "processId")
  public void handle(final UserCreationProcessCreated event) {
    pin = event.getPin();
    displayName = event.getDisplayName();
    username = event.getUsername();
    password = event.getPassword();
    terminationToken = eventScheduler.schedule(Duration.ofDays(30), UserCreationProcessTerminated.builder()
      .processId(event.getProcessId())
      .build());
  }

  @SagaEventHandler(associationProperty = "processId")
  public void handle(final ConfirmUserEvent event) {
    if (pin != null && pin.equals(event.getPin())) {

      commandGateway.sendAndWait(ConfirmedUserCommand.builder()
        .processId(event.getProcessId())
        .build());

      commandGateway.sendAndWait(CreateUserCommand.builder()
        .userId(UUID.randomUUID().toString())
        .displayName(displayName)
        .username(username)
        .password(password)
        .build());

      eventScheduler.cancelSchedule(terminationToken);
      SagaLifecycle.end();
    }
  }

  @SagaEventHandler(associationProperty = "processId")
  public void handle(final NewUserPinEvent event) {
    pin = event.getPin();
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "processId")
  public void handle(final UserCreationProcessTerminated event) {

  }
}
