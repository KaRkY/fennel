package org.fennel.users.commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.fennel.users.api.PermissionName;
import org.fennel.users.api.RoleName;
import org.fennel.users.api.commands.AddPermissionToGroupCommand;
import org.fennel.users.api.commands.AddRoleToGroupCommand;
import org.fennel.users.api.commands.AddUserToGroupCommand;
import org.fennel.users.api.commands.CreateGroupCommand;
import org.fennel.users.api.commands.RemovePermissionFromGroupCommand;
import org.fennel.users.api.commands.RemoveRoleFromGroupCommand;
import org.fennel.users.api.events.GroupCreatedEvent;
import org.fennel.users.api.events.PermissionAddedToGroupEvent;
import org.fennel.users.api.events.PermissionRemovedFromGroupEvent;
import org.fennel.users.api.events.RoleAddedToGroupEvent;
import org.fennel.users.api.events.RoleRemovedFromGroupEvent;
import org.fennel.users.api.events.UserAddedToGroupEvent;

@Aggregate
public class Group implements Serializable {
  private static final long serialVersionUID = -1774630893759721415L;

  @AggregateIdentifier
  private String                     groupName;
  private final List<RoleName>       roles       = new ArrayList<>();
  private final List<PermissionName> permissions = new ArrayList<>();

  public Group() {
  }

  @CommandHandler
  public Group(final CreateGroupCommand command) {
    AggregateLifecycle.apply(GroupCreatedEvent.builder()
      .groupName(command.getGroupName())
      .description(command.getDescription())
      .build());
  }

  @CommandHandler
  public void handle(final AddRoleToGroupCommand command) {
    if (!roles.contains(command.getRoleName())) {
      AggregateLifecycle.apply(RoleAddedToGroupEvent.builder()
        .groupName(command.getGroupName())
        .roleName(command.getRoleName())
        .build());
    }
  }

  @CommandHandler
  public void handle(final RemoveRoleFromGroupCommand command) {
    if (roles.contains(command.getRoleName())) {
      AggregateLifecycle.apply(RoleRemovedFromGroupEvent.builder()
        .groupName(command.getGroupName())
        .roleName(command.getRoleName())
        .build());
    }
  }

  @CommandHandler
  public void handle(final AddPermissionToGroupCommand command) {
    if (!permissions.contains(command.getPermissionName())) {
      AggregateLifecycle.apply(PermissionAddedToGroupEvent.builder()
        .groupName(command.getGroupName())
        .permissionName(command.getPermissionName())
        .build());
    }
  }

  @CommandHandler
  public void handle(final RemovePermissionFromGroupCommand command) {
    if (permissions.contains(command.getPermissionName())) {
      AggregateLifecycle.apply(PermissionRemovedFromGroupEvent.builder()
        .groupName(command.getGroupName())
        .permissionName(command.getPermissionName())
        .build());
    }
  }

  @CommandHandler
  public void handle(final AddUserToGroupCommand command) {
    AggregateLifecycle.apply(UserAddedToGroupEvent.builder()
      .groupName(command.getGroupName())
      .userId(command.getUserId())
      .build());
  }

  @EventSourcingHandler
  public void on(final GroupCreatedEvent event) {
    groupName = event.getGroupName().getValue();
  }

  @EventSourcingHandler
  public void on(final RoleAddedToGroupEvent event) {
    roles.add(event.getRoleName());
  }

  @EventSourcingHandler
  public void onRoleRemoved(final RoleRemovedFromGroupEvent event) {
    roles.remove(event.getRoleName());
  }

  @EventSourcingHandler
  public void onPermissionAdded(final PermissionAddedToGroupEvent event) {
    permissions.add(event.getPermissionName());
  }

  @EventSourcingHandler
  public void onPermissionRemoved(final PermissionRemovedFromGroupEvent event) {
    permissions.remove(event.getPermissionName());
  }
}
