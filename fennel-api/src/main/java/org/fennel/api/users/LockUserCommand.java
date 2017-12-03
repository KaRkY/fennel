package org.fennel.api.users;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

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
