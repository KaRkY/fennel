package org.fennel.users.command.usercreationprocess;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.common.util.AuthorizationData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Builder
@Wither
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCommand {

  @TargetAggregateIdentifier
  private final String            processId;
  private final String            pin;
  private final String            displayName;
  private final String            username;
  private final String            password;
  private final boolean           confirmed;
  private final AuthorizationData authorizationData;

}
