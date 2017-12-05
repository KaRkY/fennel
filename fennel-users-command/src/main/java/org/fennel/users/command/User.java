package org.fennel.users.command;

import java.io.Serializable;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.fennel.api.users.Password;
import org.fennel.api.users.UserId;
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
  private String   pin;
  private boolean  locked    = false;
  private boolean  confirmed = false;

  public User() {
  }

  @CommandHandler
  public User(final CreateUserCommand command) {
    AggregateLifecycle.apply(new UserCreationRequestedEvent(
      command.getUserId(),
      command.getDisplayName(),
      command.getUsername(),
      command.getPassword(),
      command.getPin()));
  }

  @CommandHandler
  public boolean handleConfirmUserCommand(final ConfirmUserCommand activateUserCommand) {
    AggregateLifecycle.apply(new UserConfirmationRequestedEvent(userId, activateUserCommand.getPin()));
    if (pin != null && pin.equals(activateUserCommand.getPin())) {
      AggregateLifecycle.apply(new UserConfirmedEvent(userId));
      AggregateLifecycle.apply(new UserCreatedEvent(userId, displayName, username, password));
      return true;
    } else return pin == null;
  }

  @CommandHandler
  public void handleNewUserPinCommand(final NewUserPinCommand newUserPinCommand) {
    AggregateLifecycle.apply(new NewUserPinEvent(userId, newUserPinCommand.getPin()));
  }

  @CommandHandler
  public void handleLockUserCommand(final LockUserCommand lockUserCommand) {
    if (!locked) {
      AggregateLifecycle.apply(new UserLockedEvent(userId));
    }
  }

  @CommandHandler
  public void handleLockUserCommand(final UnlockUserCommand unlockUserCommand) {
    if (locked) {
      AggregateLifecycle.apply(new UserUnlockedEvent(userId));
    }
  }

  @CommandHandler
  public boolean handleAuthorizeCommand(final AuthorizeCommand authorizeCommand) {
    if (username.equals(authorizeCommand.getUsername()) &&
      password.equals(authorizeCommand.getPassword()) &&
      !locked &&
      confirmed) {
      AggregateLifecycle.apply(new UserAuthorizedEvent(userId));
      return true;
    } else {
      AggregateLifecycle.apply(new UserAuthorizationFailedEvent(userId, confirmed, locked));
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
