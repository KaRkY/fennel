package org.fennel.api.tasks.events;

import org.fennel.api.tasks.TaskId;

public class TaskCreatedEvent {

  private final TaskId taskId;

  public TaskCreatedEvent(final TaskId taskId) {
    this.taskId = taskId;
  }

  public TaskId getTaskId() {
    return taskId;
  }

}
