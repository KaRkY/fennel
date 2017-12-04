package org.fennel.api.users.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.api.users.Password;
import org.fennel.api.users.UserId;
import org.fennel.api.users.Username;

public class CreateUserCommand {

  @TargetAggregateIdentifier
  private final UserId   userId;
  private final String   displayName;
  private final Username username;
  private final Password password;
  private final String   pin;

  public CreateUserCommand(
    final UserId userId,
    final String displayName,
    final Username username,
    final Password password,
    final String pin) {
    this.userId = userId;
    this.displayName = displayName;
    this.username = username;
    this.password = password;
    this.pin = pin;
  }

  public UserId getUserId() {
    return userId;
  }

  public String getDisplayName() {
    return displayName;
  }

  public Username getUsername() {
    return username;
  }

  public Password getPassword() {
    return password;
  }

  public String getPin() {
    return pin;
  }
}
