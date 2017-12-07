package org.fennel.api.users.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.api.users.UserId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UnlockUserCommand {

  @TargetAggregateIdentifier
  private final UserId userId;

}