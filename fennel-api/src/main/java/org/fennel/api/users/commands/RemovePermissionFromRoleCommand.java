package org.fennel.api.users.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.api.users.PermissionName;
import org.fennel.api.users.RoleName;

public class RemovePermissionFromRoleCommand {

  @TargetAggregateIdentifier
  private final RoleName       roleName;
  private final PermissionName permissionName;

  public RemovePermissionFromRoleCommand(final RoleName roleName, final PermissionName permissionName) {
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
