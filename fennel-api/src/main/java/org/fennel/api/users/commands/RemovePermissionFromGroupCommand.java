package org.fennel.api.users.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.api.users.GroupName;
import org.fennel.api.users.PermissionName;

public class RemovePermissionFromGroupCommand {

  @TargetAggregateIdentifier
  private final GroupName      groupName;
  private final PermissionName permissionName;

  public RemovePermissionFromGroupCommand(final GroupName groupName, final PermissionName permissionName) {
    this.groupName = groupName;
    this.permissionName = permissionName;
  }

  public GroupName getGroupName() {
    return groupName;
  }

  public PermissionName getPermissionName() {
    return permissionName;
  }

}
