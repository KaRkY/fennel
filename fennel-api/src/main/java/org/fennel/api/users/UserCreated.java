package org.fennel.api.users;

public class UserCreated {

  private final UserId userId;
  private final String displayName;
  private final String username;
  private final String password;

  public UserCreated(final UserId userId, final String displayName, final String username, final String password) {
    this.userId = userId;
    this.displayName = displayName;
    this.username = username;
    this.password = password;
  }

  public UserId getUserId() {
    return userId;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

}
