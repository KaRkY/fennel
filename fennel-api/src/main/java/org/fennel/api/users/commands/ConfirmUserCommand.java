package org.fennel.api.users.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.api.users.UserId;

public class ConfirmUserCommand {

  @TargetAggregateIdentifier
  private final UserId userId;
  private final String pin;

  public ConfirmUserCommand(final UserId userId, final String pin) {
    this.userId = userId;
    this.pin = pin;
  }

  public UserId getUserId() {
    return userId;
  }

  public String getPin() {
    return pin;
  }
}
