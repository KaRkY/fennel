package org.fennel.users.commands;

import java.io.Serializable;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.fennel.users.api.commands.CreateUserCreationProcessCommand;
import org.fennel.users.api.events.UserCreationProcessCreated;

@Aggregate
public class UserCreationProcess implements Serializable {
  private static final long serialVersionUID = -6354945156329896171L;

  @AggregateIdentifier
  private String processId;
  private String pin;

  public UserCreationProcess() {
  }

  @CommandHandler
  public UserCreationProcess(final CreateUserCreationProcessCommand command) {
    AggregateLifecycle.apply(UserCreationProcessCreated.builder()
        .processId(command.getProcessId())
        .pin(command.getPin())
        .build());
  }

  @EventSourcingHandler
  public void on(final UserCreationProcessCreated event) {
    processId = event.getProcessId();
    pin = event.getPin();
  }
}
