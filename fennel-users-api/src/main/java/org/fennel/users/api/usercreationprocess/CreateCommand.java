package org.fennel.users.api.usercreationprocess;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCommand {

  @TargetAggregateIdentifier
  private final String processId;
  private final String pin;
  private final String displayName;
  private final String username;
  private final String password;

}
