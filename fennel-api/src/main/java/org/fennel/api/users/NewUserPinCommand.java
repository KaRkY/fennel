package org.fennel.api.users;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

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
