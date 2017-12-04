package org.fennel.api.users.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.api.users.PermissionName;

public class CreatePermissionCommand {

  @TargetAggregateIdentifier
  private final PermissionName permissionName;

  public CreatePermissionCommand(final PermissionName permissionName) {
    this.permissionName = permissionName;
  }

  public PermissionName getPermissionName() {
    return permissionName;
  }
}
