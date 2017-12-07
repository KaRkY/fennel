package org.fennel.users.api.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.users.api.RoleName;
import org.fennel.users.api.UserId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddRoleToUserCommand {

  private final UserId   userId;
  private final RoleName roleName;

  @TargetAggregateIdentifier
  public String getTargetAggregateIdentifier() {
    return userId.getValue();
  }
}
