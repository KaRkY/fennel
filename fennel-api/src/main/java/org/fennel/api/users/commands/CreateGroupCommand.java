package org.fennel.api.users.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.api.users.GroupName;

public class CreateGroupCommand {

  @TargetAggregateIdentifier
  private final GroupName groupName;

  public CreateGroupCommand(final GroupName groupName) {
    this.groupName = groupName;
  }

  public GroupName getGroupName() {
    return groupName;
  }
}
