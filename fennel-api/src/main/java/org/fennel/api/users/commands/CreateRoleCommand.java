package org.fennel.api.users.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.api.users.RoleName;

public class CreateRoleCommand {

  @TargetAggregateIdentifier
  private final RoleName roleName;

  public CreateRoleCommand(final RoleName roleName) {
    this.roleName = roleName;
  }

  public RoleName getRoleName() {
    return roleName;
  }
}
