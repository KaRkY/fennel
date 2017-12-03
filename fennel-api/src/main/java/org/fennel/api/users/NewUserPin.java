package org.fennel.api.users;

public class NewUserPin {

  private final UserId userId;
  private final String pin;

  public NewUserPin(final UserId userId, final String pin) {
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
