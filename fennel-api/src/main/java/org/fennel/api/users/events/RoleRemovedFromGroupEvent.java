package org.fennel.api.users.events;

import org.fennel.api.users.GroupName;
import org.fennel.api.users.RoleName;

public class RoleRemovedFromGroupEvent {

  private final GroupName groupName;
  private final RoleName  roleName;

  public RoleRemovedFromGroupEvent(final GroupName groupName, final RoleName roleName) {
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
