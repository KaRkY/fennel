package org.fennel.api.tasks;

import java.io.Serializable;
import java.util.Objects;

public class TaskId implements Serializable {
  private static final long serialVersionUID = 7411168692662174955L;
  private final String      taskId;

  private TaskId(final String taskId) {
    super();
    this.taskId = taskId;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }

    final TaskId other = (TaskId) o;

    return Objects.equals(taskId, other.taskId);

  }

  @Override
  public int hashCode() {
    return taskId.hashCode();
  }

  @Override
  public String toString() {
    return taskId;
  }

  public static TaskId of(final String taskId) {
    return new TaskId(Objects.requireNonNull(taskId, "taskId"));
  }
}
