package org.fennel.api.users.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.api.users.GroupName;
import org.fennel.api.users.RoleName;

public class RemoveRoleFromGroupCommand {

  @TargetAggregateIdentifier
  private final GroupName groupName;
  private final RoleName  roleName;

  public RemoveRoleFromGroupCommand(final GroupName groupName, final RoleName roleName) {
    this.groupName = groupName;
    this.roleName = roleName;
  }

  public GroupName getGroupName() {
    return groupName;
  }

  public RoleName getRoleName() {
    return roleName;
  }
}
