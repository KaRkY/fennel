package org.fennel.api.users.events;

import org.fennel.api.users.UserId;

public class UserConfirmedEvent {

  private final UserId userId;

  public UserConfirmedEvent(final UserId userId) {
    this.userId = userId;
  }

  public UserId getUserId() {
    return userId;
  }
}