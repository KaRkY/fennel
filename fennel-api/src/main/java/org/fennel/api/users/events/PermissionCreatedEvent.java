package org.fennel.api.users.events;

import org.fennel.api.users.PermissionName;

public class PermissionCreatedEvent {

  private final PermissionName permissionName;

  public PermissionCreatedEvent(final PermissionName permissionName) {
    this.permissionName = permissionName;
  }

  public PermissionName getPermissionName() {
    return permissionName;
  }
}
