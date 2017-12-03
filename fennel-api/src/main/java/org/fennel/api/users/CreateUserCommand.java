package org.fennel.api.users;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class CreateUserCommand {

  @TargetAggregateIdentifier
  private final UserId userId;
  private final String displayName;
  private final String username;
  private final String password;
  private final String pin;

  public CreateUserCommand(
    final UserId userId,
    final String displayName,
    final String username,
    final String password,
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

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getPin() {
    return pin;
  }
}
