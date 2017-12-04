package org.fennel.api.users.events;

import org.fennel.api.users.PermissionName;
import org.fennel.api.users.RoleName;

public class PermissionRemovedFromRoleEvent {

  private final RoleName       roleName;
  private final PermissionName permissionName;

  public PermissionRemovedFromRoleEvent(final RoleName roleName, final PermissionName permissionName) {
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
