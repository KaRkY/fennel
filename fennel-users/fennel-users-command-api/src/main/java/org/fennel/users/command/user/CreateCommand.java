package org.fennel.users.command.user;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.common.util.UserData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCommand {

  @NonNull
  @TargetAggregateIdentifier
  private final String   userId;
  @NonNull
  private final String   displayName;
  @NonNull
  private final String   username;
  private final String   password;
  private final String   processId;
  @NonNull
  private final UserType type;
  @NonNull
  private final UserData userData;

}
