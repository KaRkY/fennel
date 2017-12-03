package org.fennel.api.users;

public class UserUnlocked {

  private final UserId userId;

  public UserUnlocked(final UserId userId) {
    this.userId = userId;
  }

  public UserId getUserId() {
    return userId;
  }

}
