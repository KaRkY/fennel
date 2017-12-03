package org.fennel.api.users;

public class UserConfirmationRequested {

  private final UserId userId;
  private final String pin;

  public UserConfirmationRequested(final UserId userId, final String pin) {
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
