package org.fennel.users.api.commands;

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
public class AuthorizeCommand {

  @TargetAggregateIdentifier
  private final String userId;
  private final String username;
  private final String password;

}
