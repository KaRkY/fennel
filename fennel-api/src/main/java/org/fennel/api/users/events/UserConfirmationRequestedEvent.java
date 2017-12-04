package org.fennel.api.users.events;

import org.fennel.api.users.UserId;

public class UserConfirmationRequestedEvent {

  private final UserId userId;
  private final String pin;

  public UserConfirmationRequestedEvent(final UserId userId, final String pin) {
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
