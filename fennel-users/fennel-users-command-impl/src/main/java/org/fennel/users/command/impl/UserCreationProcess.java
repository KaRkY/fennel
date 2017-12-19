package org.fennel.users.command.impl;

import java.io.Serializable;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.fennel.users.command.usercreationprocess.ConfirmCommand;
import org.fennel.users.command.usercreationprocess.ConfirmEvent;
import org.fennel.users.command.usercreationprocess.ConfirmedCommand;
import org.fennel.users.command.usercreationprocess.ConfirmedEvent;
import org.fennel.users.command.usercreationprocess.CreateCommand;
import org.fennel.users.command.usercreationprocess.CreatedEvent;
import org.fennel.users.command.usercreationprocess.DataCheckCommand;
import org.fennel.users.command.usercreationprocess.DataCheckFailedEvent;
import org.fennel.users.command.usercreationprocess.DataCheckedEvent;
import org.fennel.users.command.usercreationprocess.FailCheckCommand;
import org.fennel.users.command.usercreationprocess.NewPinCommand;
import org.fennel.users.command.usercreationprocess.NewPinEvent;
import org.fennel.users.command.usercreationprocess.TerminateCommand;
import org.fennel.users.command.usercreationprocess.TerminatedEvent;
import org.fennel.users.command.usercreationprocess.UserCreationProcessState;

@Aggregate
public class UserCreationProcess implements Serializable {
  private static final long serialVersionUID = -6354945156329896171L;

  @AggregateIdentifier
  private String                   processId;
  private UserCreationProcessState state;

  public UserCreationProcess() {
  }

  @CommandHandler
  public UserCreationProcess(final CreateCommand command) {
    AggregateLifecycle.apply(CreatedEvent.builder()
      .processId(command.getProcessId())
      .pin(command.getPin())
      .displayName(command.getDisplayName())
      .username(command.getUsername())
      .password(command.getPassword())
      .build());
  }

  @CommandHandler
  public void handle(final ConfirmCommand command) {
    if (state != UserCreationProcessState.CHECKED)
      throw new IllegalStateException("Process not in checked state.");

    AggregateLifecycle.apply(ConfirmEvent.builder()
      .processId(processId)
      .pin(command.getPin())
      .build());
  }

  @CommandHandler
  public void handle(final ConfirmedCommand command) {
    AggregateLifecycle.apply(ConfirmedEvent.builder()
      .processId(processId)
      .build());
  }

  @CommandHandler
  public void handle(final NewPinCommand command) {
    AggregateLifecycle.apply(NewPinEvent.builder()
      .processId(processId)
      .pin(command.getPin())
      .build());
  }

  @CommandHandler
  public void handle(final DataCheckCommand command) {
    if (state != UserCreationProcessState.PENDING_CHECK)
      throw new IllegalStateException("Process not in pending check state.");

    AggregateLifecycle.apply(DataCheckedEvent.builder()
      .processId(processId)
      .build());
  }

  @CommandHandler
  public void handle(final TerminateCommand command) {
    AggregateLifecycle.apply(TerminatedEvent.builder()
      .processId(processId)
      .build());
  }

  @CommandHandler
  public void handle(final FailCheckCommand command) {
    AggregateLifecycle.apply(DataCheckFailedEvent.builder()
      .processId(processId)
      .build());
  }

  @EventSourcingHandler
  public void on(final CreatedEvent event) {
    processId = event.getProcessId();
    state = UserCreationProcessState.PENDING_CHECK;
  }

  @EventSourcingHandler
  public void on(final DataCheckedEvent event) {
    state = UserCreationProcessState.CHECKED;
  }

  @EventSourcingHandler
  public void on(final ConfirmedEvent event) {
    state = UserCreationProcessState.CONFIRMED;
  }

  @EventSourcingHandler
  public void on(final TerminatedEvent event) {
    state = UserCreationProcessState.TERMINATED;
  }

  @EventSourcingHandler
  public void on(final DataCheckFailedEvent event) {
    state = UserCreationProcessState.CHECK_FAILED;
  }
}
