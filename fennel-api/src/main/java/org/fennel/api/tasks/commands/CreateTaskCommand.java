package org.fennel.api.tasks.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.api.tasks.TaskId;

public class CreateTaskCommand {

  @TargetAggregateIdentifier
  private final TaskId taskId;

  public CreateTaskCommand(final TaskId taskId) {
    this.taskId = taskId;
  }

  public TaskId getTaskId() {
    return taskId;
  }

}
