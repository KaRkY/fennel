package org.fennel.api.users.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.api.users.Password;
import org.fennel.api.users.UserId;
import org.fennel.api.users.Username;

public class AuthorizeCommand {

  @TargetAggregateIdentifier
  private final UserId   userId;
  private final Username username;
  private final Password password;

  public AuthorizeCommand(final UserId userId, final Username username, final Password password) {
    this.userId = userId;
    this.username = username;
    this.password = password;
  }

  public UserId getUserId() {
    return userId;
  }

  public Username getUsername() {
    return username;
  }

  public Password getPassword() {
    return password;
  }
}
