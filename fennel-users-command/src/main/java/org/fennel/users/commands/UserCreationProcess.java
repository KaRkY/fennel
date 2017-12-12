package org.fennel.users.commands;

import java.io.Serializable;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.fennel.users.api.commands.ConfirmUserCommand;
import org.fennel.users.api.commands.ConfirmedUserCommand;
import org.fennel.users.api.commands.CreateUserCreationProcessCommand;
import org.fennel.users.api.commands.NewUserPinCommand;
import org.fennel.users.api.events.ConfirmUserEvent;
import org.fennel.users.api.events.NewUserPinEvent;
import org.fennel.users.api.events.UserConfirmedEvent;
import org.fennel.users.api.events.UserCreationProcessCreated;

@Aggregate
public class UserCreationProcess implements Serializable {
  private static final long serialVersionUID = -6354945156329896171L;

  @AggregateIdentifier
  private String processId;

  public UserCreationProcess() {
  }

  @CommandHandler
  public UserCreationProcess(final CreateUserCreationProcessCommand command) {
    AggregateLifecycle.apply(UserCreationProcessCreated.builder()
      .processId(command.getProcessId())
      .pin(command.getPin())
      .displayName(command.getDisplayName())
      .username(command.getUsername())
      .password(command.getPassword())
      .build());
  }

  @CommandHandler
  public void handle(final ConfirmUserCommand command) {
    AggregateLifecycle.apply(ConfirmUserEvent.builder()
      .processId(processId)
      .pin(command.getPin())
      .build());
  }

  @CommandHandler
  public void handle(final ConfirmedUserCommand command) {
    AggregateLifecycle.apply(UserConfirmedEvent.builder()
      .processId(processId)
      .build());
  }

  @CommandHandler
  public void handle(final NewUserPinCommand command) {
    AggregateLifecycle.apply(NewUserPinEvent.builder()
      .processId(processId)
      .pin(command.getPin())
      .build());
  }

  @EventSourcingHandler
  public void on(final UserCreationProcessCreated event) {
    processId = event.getProcessId();
  }
}
