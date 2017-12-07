package org.fennel.users.api.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.users.api.Password;
import org.fennel.users.api.UserId;
import org.fennel.users.api.Username;

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

  private final UserId   userId;
  private final Username username;
  private final Password password;

  @TargetAggregateIdentifier
  public String getTargetAggregateIdentifier() {
    return userId.getValue();
  }
}
