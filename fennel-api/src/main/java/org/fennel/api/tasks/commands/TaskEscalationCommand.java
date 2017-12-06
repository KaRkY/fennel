package org.fennel.api.tasks.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.api.tasks.TaskId;

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

  @TargetAggregateIdentifier
  private final TaskId taskId;

}
