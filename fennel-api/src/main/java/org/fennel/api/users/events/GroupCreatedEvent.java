package org.fennel.api.users.events;

import org.fennel.api.users.GroupName;

public class GroupCreatedEvent {

  private final GroupName groupName;

  public GroupCreatedEvent(final GroupName groupName) {
    this.groupName = groupName;
  }

  public GroupName getGroupName() {
    return groupName;
  }
}
