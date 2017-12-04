package org.fennel.api.users.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.api.users.UserId;

public class NewUserPinCommand {

  @TargetAggregateIdentifier
  private final UserId userId;
  private final String pin;

  public NewUserPinCommand(final UserId userId, final String pin) {
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
