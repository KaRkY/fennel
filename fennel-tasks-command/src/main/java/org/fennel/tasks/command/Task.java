package org.fennel.tasks.command;

import java.io.Serializable;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.fennel.api.tasks.TaskId;
import org.fennel.api.tasks.commands.CreateTaskCommand;
import org.fennel.api.tasks.events.TaskCreatedEvent;

@Aggregate
public class Task implements Serializable {
  private static final long serialVersionUID = 8562075175602085255L;

  @AggregateIdentifier
  private TaskId taskId;

  public Task() {
  }

  @CommandHandler
  public Task(final CreateTaskCommand command) {
    AggregateLifecycle.apply(new TaskCreatedEvent(command.getTaskId()));
  }

  @EventSourcingHandler
  public void onTaskCreatedEvent(final TaskCreatedEvent event) {
    taskId = event.getTaskId();
  }
}
