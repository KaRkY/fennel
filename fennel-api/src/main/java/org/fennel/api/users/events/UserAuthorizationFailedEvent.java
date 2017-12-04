package org.fennel.api.users.events;

import org.fennel.api.users.UserId;

public class UserAuthorizationFailedEvent {

  private final UserId  userId;
  private final boolean confirmed;
  private final boolean locked;

  public UserAuthorizationFailedEvent(final UserId userId, final boolean confirmed, final boolean locked) {
    this.userId = userId;
    this.confirmed = confirmed;
    this.locked = locked;
  }

  public UserId getUserId() {
    return userId;
  }

  public boolean isConfirmed() {
    return confirmed;
  }

  public boolean isLocked() {
    return locked;
  }
}
