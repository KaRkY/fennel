package org.fennel.api.users;

public class UserConfirmed {

  private final UserId userId;

  public UserConfirmed(final UserId userId) {
    this.userId = userId;
  }

  public UserId getUserId() {
    return userId;
  }
}
