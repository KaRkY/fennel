package org.fennel.api.users.events;

import org.fennel.api.users.Password;
import org.fennel.api.users.UserId;
import org.fennel.api.users.Username;

public class UserCreatedEvent {

  private final UserId   userId;
  private final String   displayName;
  private final Username username;
  private final Password password;

  public UserCreatedEvent(final UserId userId, final String displayName, final Username username, final Password password) {
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

  public Username getUsername() {
    return username;
  }

  public Password getPassword() {
    return password;
  }

}
