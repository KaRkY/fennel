package org.fennel.users.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.fennel.api.users.GroupName;
import org.fennel.api.users.PermissionName;
import org.fennel.api.users.RoleName;
import org.fennel.api.users.commands.AddPermissionToGroupCommand;
import org.fennel.api.users.commands.AddRoleToGroupCommand;
import org.fennel.api.users.commands.CreateGroupCommand;
import org.fennel.api.users.commands.RemovePermissionFromGroupCommand;
import org.fennel.api.users.commands.RemoveRoleFromGroupCommand;
import org.fennel.api.users.events.GroupCreatedEvent;
import org.fennel.api.users.events.PermissionAddedToGroupEvent;
import org.fennel.api.users.events.PermissionRemovedFromGroupEvent;
import org.fennel.api.users.events.RoleAddedToGroupEvent;
import org.fennel.api.users.events.RoleRemovedFromGroupEvent;

@Aggregate
public class Group implements Serializable {
  private static final long serialVersionUID = -1774630893759721415L;

  @AggregateIdentifier
  private GroupName                  groupName;
  private final List<RoleName>       roles       = new ArrayList<>();
  private final List<PermissionName> permissions = new ArrayList<>();

  public Group() {
  }

  @CommandHandler
  public Group(final CreateGroupCommand command) {
    AggregateLifecycle.apply(new GroupCreatedEvent(command.getGroupName()));
  }

  @CommandHandler
  public void handleAddRoleCommand(final AddRoleToGroupCommand command) {
    if (!roles.contains(command.getRoleName())) {
      AggregateLifecycle.apply(new RoleAddedToGroupEvent(command.getGroupName(), command.getRoleName()));
    }
  }

  @CommandHandler
  public void handleRemoveRoleCommand(final RemoveRoleFromGroupCommand command) {
    if (roles.contains(command.getRoleName())) {
      AggregateLifecycle.apply(new RoleRemovedFromGroupEvent(command.getGroupName(), command.getRoleName()));
    }
  }

  @CommandHandler
  public void handleAddPermissionCommand(final AddPermissionToGroupCommand command) {
    if (!permissions.contains(command.getPermissionName())) {
      AggregateLifecycle.apply(new PermissionAddedToGroupEvent(command.getGroupName(), command.getPermissionName()));
    }
  }

  @CommandHandler
  public void handleRemovePermissionCommand(final RemovePermissionFromGroupCommand command) {
    if (permissions.contains(command.getPermissionName())) {
      AggregateLifecycle
        .apply(new PermissionRemovedFromGroupEvent(command.getGroupName(), command.getPermissionName()));
    }
  }

  @EventSourcingHandler
  public void onGroupCreated(final GroupCreatedEvent event) {
    groupName = event.getGroupName();
  }

  @EventSourcingHandler
  public void onRoleAdded(final RoleAddedToGroupEvent event) {
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
