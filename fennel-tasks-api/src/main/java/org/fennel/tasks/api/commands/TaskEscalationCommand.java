package org.fennel.tasks.api.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.tasks.api.TaskId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskEscalationCommand {

  private final TaskId taskId;

  @TargetAggregateIdentifier
  public String getTargetAggregateIdentifier() {
    return taskId.getValue();
  }

}
