package org.fennel.users.api.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.users.api.GroupName;
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
public class AddUserToGroupCommand {

  private final GroupName groupName;
  private final UserId    userId;

  @TargetAggregateIdentifier
  public String getTargetAggregateIdentifier() {
    return groupName.getValue();
  }
}
