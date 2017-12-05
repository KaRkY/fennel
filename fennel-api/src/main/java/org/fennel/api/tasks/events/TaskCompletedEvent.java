package org.fennel.api.tasks.events;

import org.fennel.api.tasks.TaskId;

public class TaskCompletedEvent {

  private final TaskId taskId;

  public TaskCompletedEvent(final TaskId taskId) {
    this.taskId = taskId;
  }

  public TaskId getTaskId() {
    return taskId;
  }

}
