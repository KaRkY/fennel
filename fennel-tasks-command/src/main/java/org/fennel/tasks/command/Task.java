package org.fennel.tasks.command;

import java.io.Serializable;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.fennel.tasks.api.commands.CreateTaskCommand;
import org.fennel.tasks.api.events.TaskCreatedEvent;

@Aggregate
public class Task implements Serializable {
  private static final long serialVersionUID = 8562075175602085255L;

  @AggregateIdentifier
  private String taskId;

  public Task() {
  }

  @CommandHandler
  public Task(final CreateTaskCommand command) {
    AggregateLifecycle.apply(TaskCreatedEvent.builder()
      .taskId(command.getTaskId())
      .build());
  }

  @EventSourcingHandler
  public void onTaskCreatedEvent(final TaskCreatedEvent event) {
    taskId = event.getTaskId();
  }
}
