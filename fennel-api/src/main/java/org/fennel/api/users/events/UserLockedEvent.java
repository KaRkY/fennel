package org.fennel.api.users.events;

import org.fennel.api.users.UserId;

public class UserLockedEvent {

  private final UserId userId;

  public UserLockedEvent(final UserId userId) {
    this.userId = userId;
  }

  public UserId getUserId() {
    return userId;
  }

}
