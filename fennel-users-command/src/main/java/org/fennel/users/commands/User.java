package org.fennel.users.commands;

import java.io.Serializable;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.fennel.users.api.Password;
import org.fennel.users.api.UserPin;
import org.fennel.users.api.Username;
import org.fennel.users.api.commands.AddRoleToUserCommand;
import org.fennel.users.api.commands.AuthorizeCommand;
import org.fennel.users.api.commands.ConfirmUserCommand;
import org.fennel.users.api.commands.CreateUserCommand;
import org.fennel.users.api.commands.LockUserCommand;
import org.fennel.users.api.commands.NewUserPinCommand;
import org.fennel.users.api.commands.UnlockUserCommand;
import org.fennel.users.api.events.ConfirmUserEvent;
import org.fennel.users.api.events.NewUserPinEvent;
import org.fennel.users.api.events.RoleAddedToUserEvent;
import org.fennel.users.api.events.UserAuthorizationFailedEvent;
import org.fennel.users.api.events.UserAuthorizedEvent;
import org.fennel.users.api.events.UserConfirmedEvent;
import org.fennel.users.api.events.UserCreatedEvent;
import org.fennel.users.api.events.UserLockedEvent;
import org.fennel.users.api.events.UserUnlockedEvent;

@Aggregate
public class User implements Serializable {
  private static final long serialVersionUID = -1774630893759721415L;

  @AggregateIdentifier
  private String   userId;
  private Username username;
  private Password password;
  private UserPin  pin;
  private boolean  locked;
  private boolean  confirmed;

  public User() {
  }

  @CommandHandler
  public User(final CreateUserCommand command) {
    AggregateLifecycle.apply(UserCreatedEvent.builder()
      .userId(command.getUserId())
      .displayName(command.getDisplayName())
      .username(command.getUsername())
      .password(command.getPassword())
      .pin(command.getPin())
      .confirmed(false)
      .locked(false)
      .build());
  }

  @CommandHandler
  public boolean handle(final ConfirmUserCommand command) {
    AggregateLifecycle.apply(ConfirmUserEvent.builder()
      .userId(command.getUserId())
      .pin(command.getPin())
      .build());
    if (pin != null && pin.equals(command.getPin())) {
      AggregateLifecycle.apply(UserConfirmedEvent.builder()
        .userId(command.getUserId())
        .build());
      return true;
    } else return pin == null;
  }

  @CommandHandler
  public void handle(final NewUserPinCommand command) {
    AggregateLifecycle.apply(NewUserPinEvent.builder()
      .userId(command.getUserId())
      .pin(command.getPin())
      .build());
  }

  @CommandHandler
  public void handle(final LockUserCommand command) {
    if (!locked) {
      AggregateLifecycle.apply(UserLockedEvent.builder()
        .userId(command.getUserId())
        .build());
    }
  }

  @CommandHandler
  public void handle(final UnlockUserCommand command) {
    if (locked) {
      AggregateLifecycle.apply(UserUnlockedEvent.builder()
        .userId(command.getUserId())
        .build());
    }
  }

  @CommandHandler
  public boolean handle(final AuthorizeCommand command) {
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

  @CommandHandler
  public void handle(final AddRoleToUserCommand command) {
    AggregateLifecycle.apply(RoleAddedToUserEvent.builder()
      .userId(command.getUserId())
      .roleName(command.getRoleName())
      .build());
  }

  @EventSourcingHandler
  public void on(final UserCreatedEvent event) {
    userId = event.getUserId().getValue();
    username = event.getUsername();
    password = event.getPassword();
    pin = event.getPin();
    confirmed = event.isConfirmed();
    locked = event.isLocked();
  }

  @EventSourcingHandler
  public void on(final UserConfirmedEvent event) {
    pin = null;
    confirmed = true;
  }

  @EventSourcingHandler
  public void on(final NewUserPinEvent event) {
    pin = event.getPin();
  }

  @EventSourcingHandler
  public void on(final UserLockedEvent event) {
    locked = true;
  }

  @EventSourcingHandler
  public void on(final UserUnlockedEvent event) {
    locked = false;
  }
}
