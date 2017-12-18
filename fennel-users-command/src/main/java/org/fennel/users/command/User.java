package org.fennel.users.command;

import java.io.Serializable;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.fennel.users.api.user.AddRoleCommand;
import org.fennel.users.api.user.AuthorizationFailedEvent;
import org.fennel.users.api.user.AuthorizeCommand;
import org.fennel.users.api.user.AuthorizedEvent;
import org.fennel.users.api.user.CreateCommand;
import org.fennel.users.api.user.CreatedEvent;
import org.fennel.users.api.user.FailAuthorizationCommand;
import org.fennel.users.api.user.LockCommand;
import org.fennel.users.api.user.LockedEvent;
import org.fennel.users.api.user.RoleAddedEvent;
import org.fennel.users.api.user.UnlockCommand;
import org.fennel.users.api.user.UnlockedEvent;
import org.fennel.users.api.user.UserType;

@Aggregate
public class User implements Serializable {
  private static final long serialVersionUID = -1774630893759721415L;

  @AggregateIdentifier
  private String   userId;
  private boolean  locked;
  private UserType type;

  public User() {
  }

  @CommandHandler
  public User(final CreateCommand command) {
    AggregateLifecycle.apply(CreatedEvent.builder()
      .userId(command.getUserId())
      .displayName(command.getDisplayName())
      .username(command.getUsername())
      .password(command.getPassword())
      .locked(false)
      .type(command.getType())
      .userData(command.getUserData())
      .build());
  }

  @CommandHandler
  public void handle(final LockCommand command) {
    if(UserType.SYSTEM == type) throw new UnsupportedOperationException();
    if (!locked) {
      AggregateLifecycle.apply(LockedEvent.builder()
        .userId(command.getUserId())
        .build());
    }
  }

  @CommandHandler
  public void handle(final UnlockCommand command) {
    if(UserType.SYSTEM == type) throw new UnsupportedOperationException();
    if (locked) {
      AggregateLifecycle.apply(UnlockedEvent.builder()
        .userId(command.getUserId())
        .build());
    }
  }

  @CommandHandler
  public void handle(final AuthorizeCommand command) {
    AggregateLifecycle.apply(AuthorizedEvent.builder()
      .userId(command.getUserId())
      .build());
  }

  @CommandHandler
  public boolean handle(final FailAuthorizationCommand command) {
    AggregateLifecycle.apply(AuthorizationFailedEvent.builder()
      .userId(command.getUserId())
      .build());
    return true;
  }

  @CommandHandler
  public void handle(final AddRoleCommand command) {
    AggregateLifecycle.apply(RoleAddedEvent.builder()
      .userId(command.getUserId())
      .roleName(command.getRoleName())
      .build());
  }

  @EventSourcingHandler
  public void on(final CreatedEvent event) {
    userId = event.getUserId();
    locked = event.isLocked();
    type = event.getType();
  }

  @EventSourcingHandler
  public void on(final LockedEvent event) {
    locked = true;
  }

  @EventSourcingHandler
  public void on(final UnlockedEvent event) {
    locked = false;
  }
}
