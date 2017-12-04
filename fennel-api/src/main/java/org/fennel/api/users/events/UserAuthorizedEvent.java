package org.fennel.api.users.events;

import org.fennel.api.users.UserId;

public class UserAuthorizedEvent {

  private final UserId userId;

  public UserAuthorizedEvent(final UserId userId) {
    this.userId = userId;
  }

  public UserId getUserId() {
    return userId;
  }
}
