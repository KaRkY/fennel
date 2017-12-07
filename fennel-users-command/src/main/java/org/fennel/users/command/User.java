package org.fennel.users.command;

import java.io.Serializable;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.fennel.api.users.Password;
import org.fennel.api.users.UserId;
import org.fennel.api.users.UserPin;
import org.fennel.api.users.Username;
import org.fennel.api.users.commands.AuthorizeCommand;
import org.fennel.api.users.commands.ConfirmUserCommand;
import org.fennel.api.users.commands.CreateUserCommand;
import org.fennel.api.users.commands.LockUserCommand;
import org.fennel.api.users.commands.NewUserPinCommand;
import org.fennel.api.users.commands.UnlockUserCommand;
import org.fennel.api.users.events.NewUserPinEvent;
import org.fennel.api.users.events.UserAuthorizationFailedEvent;
import org.fennel.api.users.events.UserAuthorizedEvent;
import org.fennel.api.users.events.UserConfirmationRequestedEvent;
import org.fennel.api.users.events.UserConfirmedEvent;
import org.fennel.api.users.events.UserCreatedEvent;
import org.fennel.api.users.events.UserCreationRequestedEvent;
import org.fennel.api.users.events.UserLockedEvent;
import org.fennel.api.users.events.UserUnlockedEvent;

@Aggregate
public class User implements Serializable {
  private static final long serialVersionUID = -1774630893759721415L;

  @AggregateIdentifier
  private UserId   userId;
  private String   displayName;
  private Username username;
  private Password password;
  private UserPin  pin;
  private boolean  locked    = false;
  private boolean  confirmed = false;

  public User() {
  }

  @CommandHandler
  public User(final CreateUserCommand command) {
    AggregateLifecycle.apply(UserCreationRequestedEvent.builder()
      .userId(command.getUserId())
      .displayName(command.getDisplayName())
      .username(command.getUsername())
      .password(command.getPassword())
      .pin(command.getPin())
      .build());
  }

  @CommandHandler
  public boolean handleConfirmUserCommand(final ConfirmUserCommand command) {
    AggregateLifecycle.apply(UserConfirmationRequestedEvent.builder()
      .userId(command.getUserId())
      .pin(command.getPin())
      .build());
    if (pin != null && pin.equals(command.getPin())) {
      AggregateLifecycle.apply(UserConfirmedEvent.builder()
        .userId(command.getUserId())
        .build());
      AggregateLifecycle.apply(UserCreatedEvent.builder()
        .userId(userId)
        .displayName(displayName)
        .username(username)
        .password(password)
        .build());
      return true;
    } else {
      return pin == null;
    }
  }

  @CommandHandler
  public void handleNewUserPinCommand(final NewUserPinCommand command) {
    AggregateLifecycle.apply(NewUserPinEvent.builder()
      .userId(command.getUserId())
      .pin(command.getPin())
      .build());
  }

  @CommandHandler
  public void handleLockUserCommand(final LockUserCommand command) {
    if (!locked) {
      AggregateLifecycle.apply(UserLockedEvent.builder()
        .userId(command.getUserId())
        .build());
    }
  }

  @CommandHandler
  public void handleLockUserCommand(final UnlockUserCommand command) {
    if (locked) {
      AggregateLifecycle.apply(UserUnlockedEvent.builder()
        .userId(command.getUserId())
        .build());
    }
  }

  @CommandHandler
  public boolean handleAuthorizeCommand(final AuthorizeCommand command) {
    if (username.equals(command.getUsername()) &&
      password.equals(command.getPassword()) &&
      !locked &&
      confirmed) {
      AggregateLifecycle.apply(UserAuthorizedEvent.builder()
        .userId(command.getUserId())
        .build());
      return true;
    } else {
      AggregateLifecycle.apply(UserAuthorizationFailedEvent.builder()
        .userId(command.getUserId())
        .confirmed(confirmed)
        .locked(locked)
        .build());
      return false;
    }
  }

  @EventSourcingHandler
  public void onUserCreationRequested(final UserCreationRequestedEvent event) {
    userId = event.getUserId();
    displayName = event.getDisplayName();
    username = event.getUsername();
    password = event.getPassword();
    pin = event.getPin();
  }

  @EventSourcingHandler
  public void onUserConfirmed(final UserConfirmedEvent event) {
    pin = null;
    confirmed = true;
  }

  @EventSourcingHandler
  public void onNewUserPin(final NewUserPinEvent event) {
    pin = event.getPin();
  }

  @EventSourcingHandler
  public void onUserLocked(final UserLockedEvent event) {
    locked = true;
  }

  @EventSourcingHandler
  public void onUserUnlocked(final UserUnlockedEvent event) {
    locked = false;
  }
}
