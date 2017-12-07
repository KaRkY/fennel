package org.fennel.users.api.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.users.api.PermissionName;
import org.fennel.users.api.RoleName;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddPermissionToRoleCommand {

  private final RoleName       roleName;
  private final PermissionName permissionName;

  @TargetAggregateIdentifier
  public String getTargetAggregateIdentifier() {
    return roleName.getValue();
  }
}
