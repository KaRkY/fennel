package org.fennel.api.users.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.api.users.UserId;

public class LockUserCommand {

  @TargetAggregateIdentifier
  private final UserId userId;

  public LockUserCommand(final UserId userId) {
    this.userId = userId;
  }

  public UserId getUserId() {
    return userId;
  }
}
