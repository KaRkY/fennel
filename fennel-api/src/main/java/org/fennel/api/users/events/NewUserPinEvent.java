package org.fennel.api.users.events;

import org.fennel.api.users.UserId;

public class NewUserPinEvent {

  private final UserId userId;
  private final String pin;

  public NewUserPinEvent(final UserId userId, final String pin) {
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
