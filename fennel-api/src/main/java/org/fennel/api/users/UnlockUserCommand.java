package org.fennel.api.users;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class UnlockUserCommand {

  @TargetAggregateIdentifier
  private final UserId userId;

  public UnlockUserCommand(final UserId userId) {
    this.userId = userId;
  }

  public UserId getUserId() {
    return userId;
  }

}
