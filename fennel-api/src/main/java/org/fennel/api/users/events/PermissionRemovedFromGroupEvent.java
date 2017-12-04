package org.fennel.api.users.events;

import org.fennel.api.users.GroupName;
import org.fennel.api.users.PermissionName;

public class PermissionRemovedFromGroupEvent {

  private final GroupName      groupName;
  private final PermissionName permissionName;

  public PermissionRemovedFromGroupEvent(final GroupName groupName, final PermissionName permissionName) {
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
