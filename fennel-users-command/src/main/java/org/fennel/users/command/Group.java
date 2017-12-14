package org.fennel.users.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.fennel.users.api.group.AddPermissionCommand;
import org.fennel.users.api.group.AddRoleCommand;
import org.fennel.users.api.group.AddUserCommand;
import org.fennel.users.api.group.CreateCommand;
import org.fennel.users.api.group.CreatedEvent;
import org.fennel.users.api.group.PermissionAddedEvent;
import org.fennel.users.api.group.PermissionRemovedEvent;
import org.fennel.users.api.group.RemovePermissionCommand;
import org.fennel.users.api.group.RemoveRoleCommand;
import org.fennel.users.api.group.RoleAddedEvent;
import org.fennel.users.api.group.RoleRemovedEvent;
import org.fennel.users.api.group.UserAddedEvent;

@Aggregate
public class Group implements Serializable {
  private static final long serialVersionUID = -1774630893759721415L;

  @AggregateIdentifier
  private String             groupName;
  private final List<String> roles       = new ArrayList<>();
  private final List<String> permissions = new ArrayList<>();

  public Group() {
  }

  @CommandHandler
  public Group(final CreateCommand command) {
    AggregateLifecycle.apply(CreatedEvent.builder()
      .groupName(command.getGroupName())
      .description(command.getDescription())
      .build());
  }

  @CommandHandler
  public void handle(final AddRoleCommand command) {
    if (!roles.contains(command.getRoleName())) {
      AggregateLifecycle.apply(RoleAddedEvent.builder()
        .groupName(command.getGroupName())
        .roleName(command.getRoleName())
        .build());
    }
  }

  @CommandHandler
  public void handle(final RemoveRoleCommand command) {
    if (roles.contains(command.getRoleName())) {
      AggregateLifecycle.apply(RoleRemovedEvent.builder()
        .groupName(command.getGroupName())
        .roleName(command.getRoleName())
        .build());
    }
  }

  @CommandHandler
  public void handle(final AddPermissionCommand command) {
    if (!permissions.contains(command.getPermissionName())) {
      AggregateLifecycle.apply(PermissionAddedEvent.builder()
        .groupName(command.getGroupName())
        .permissionName(command.getPermissionName())
        .build());
    }
  }

  @CommandHandler
  public void handle(final RemovePermissionCommand command) {
    if (permissions.contains(command.getPermissionName())) {
      AggregateLifecycle.apply(PermissionRemovedEvent.builder()
        .groupName(command.getGroupName())
        .permissionName(command.getPermissionName())
        .build());
    }
  }

  @CommandHandler
  public void handle(final AddUserCommand command) {
    AggregateLifecycle.apply(UserAddedEvent.builder()
      .groupName(command.getGroupName())
      .userId(command.getUserId())
      .build());
  }

  @EventSourcingHandler
  public void on(final CreatedEvent event) {
    groupName = event.getGroupName();
  }

  @EventSourcingHandler
  public void on(final RoleAddedEvent event) {
    roles.add(event.getRoleName());
  }

  @EventSourcingHandler
  public void onRoleRemoved(final RoleRemovedEvent event) {
    roles.remove(event.getRoleName());
  }

  @EventSourcingHandler
  public void onPermissionAdded(final PermissionAddedEvent event) {
    permissions.add(event.getPermissionName());
  }

  @EventSourcingHandler
  public void onPermissionRemoved(final PermissionRemovedEvent event) {
    permissions.remove(event.getPermissionName());
  }
}
