package org.fennel.api.users;

public class UserLocked {

  private final UserId userId;

  public UserLocked(final UserId userId) {
    this.userId = userId;
  }

  public UserId getUserId() {
    return userId;
  }

}
