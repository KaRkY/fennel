package org.fennel.api.users.events;

import org.fennel.api.users.PermissionName;
import org.fennel.api.users.RoleName;

public class PermissionAddedToRoleEvent {

  private final RoleName       roleName;
  private final PermissionName permissionName;

  public PermissionAddedToRoleEvent(final RoleName roleName, final PermissionName permissionName) {
    this.roleName = roleName;
    this.permissionName = permissionName;
  }

  public RoleName getRoleName() {
    return roleName;
  }

  public PermissionName getPermissionName() {
    return permissionName;
  }

}
