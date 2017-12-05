package org.fennel.api.tasks.events;

import org.fennel.api.tasks.TaskId;

public class TaskEscalationEvent {

  private final TaskId taskId;

  public TaskEscalationEvent(final TaskId taskId) {
    this.taskId = taskId;
  }

  public TaskId getTaskId() {
    return taskId;
  }

}
