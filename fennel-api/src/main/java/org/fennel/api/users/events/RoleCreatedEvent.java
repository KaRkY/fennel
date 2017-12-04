package org.fennel.api.users.events;

import org.fennel.api.users.RoleName;

public class RoleCreatedEvent {

  private final RoleName roleName;

  public RoleCreatedEvent(final RoleName roleName) {
    this.roleName = roleName;
  }

  public RoleName getRoleName() {
    return roleName;
  }
}
