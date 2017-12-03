package org.fennel.users.command;

import java.io.Serializable;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.commandhandling.model.AggregateRoot;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.fennel.api.users.ConfirmUserCommand;
import org.fennel.api.users.CreateUserCommand;
import org.fennel.api.users.LockUserCommand;
import org.fennel.api.users.NewUserPin;
import org.fennel.api.users.NewUserPinCommand;
import org.fennel.api.users.UnlockUserCommand;
import org.fennel.api.users.UserConfirmationRequested;
import org.fennel.api.users.UserConfirmed;
import org.fennel.api.users.UserCreated;
import org.fennel.api.users.UserCreationRequested;
import org.fennel.api.users.UserId;
import org.fennel.api.users.UserLocked;
import org.fennel.api.users.UserUnlocked;

@AggregateRoot
public class User implements Serializable {
  private static final long serialVersionUID = -1774630893759721415L;

  @AggregateIdentifier
  private UserId userId;
  private String displayName;
  private String username;
  private String password;
  private String pin;
  private  boolean locked = false;

  public User() {
  }

  @CommandHandler
  public User(final CreateUserCommand command) {
    AggregateLifecycle.apply(new UserCreationRequested(
      command.getUserId(),
      command.getDisplayName(),
      command.getUsername(),
      command.getPassword(),
      command.getPin()));
  }

  @CommandHandler
  public boolean handleConfirmUserCommand(final ConfirmUserCommand activateUserCommand) {
    AggregateLifecycle.apply(new UserConfirmationRequested(userId, activateUserCommand.getPin()));
    if (pin != null && pin.equals(activateUserCommand.getPin())) {
      AggregateLifecycle.apply(new UserConfirmed(userId));
      AggregateLifecycle.apply(new UserCreated(userId, displayName, username, password));
      return true;
    } else {
      return pin == null;
    }
  }

  @CommandHandler
  public void handleNewUserPinCommand(final NewUserPinCommand newUserPinCommand) {
    AggregateLifecycle.apply(new NewUserPin(userId, newUserPinCommand.getPin()));
  }

  @CommandHandler
  public void handleLockUserCommand(final LockUserCommand lockUserCommand) {
    if (!locked) {
      AggregateLifecycle.apply(new UserLocked(userId));
    }
  }

  @CommandHandler
  public void handleLockUserCommand(final UnlockUserCommand unlockUserCommand) {
    if (locked) {
      AggregateLifecycle.apply(new UserUnlocked(userId));
    }
  }

  @EventSourcingHandler
  public void onUserCreationRequested(final UserCreationRequested event) {
    userId = event.getUserId();
    displayName = event.getDisplayName();
    username = event.getUsername();
    password = event.getPassword();
    pin = event.getPin();
  }

  @EventSourcingHandler
  public void onUserConfirmed(final UserConfirmed event) {
    pin = null;
  }

  @EventSourcingHandler
  public void onNewUserPin(final NewUserPin event) {
    pin = event.getPin();
  }

  @EventSourcingHandler
  public void onUserLocked(final UserLocked event) {
    locked = true;
  }

  @EventSourcingHandler
  public void onUserUnlocked(final UserUnlocked event) {
    locked = false;
  }
}
