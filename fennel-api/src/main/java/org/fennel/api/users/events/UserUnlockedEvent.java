package org.fennel.api.users.events;

import org.fennel.api.users.UserId;

public class UserUnlockedEvent {

  private final UserId userId;

  public UserUnlockedEvent(final UserId userId) {
    this.userId = userId;
  }

  public UserId getUserId() {
    return userId;
  }

}
