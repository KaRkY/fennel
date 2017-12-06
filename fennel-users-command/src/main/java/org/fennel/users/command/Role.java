package org.fennel.users.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.fennel.api.users.PermissionName;
import org.fennel.api.users.RoleName;
import org.fennel.api.users.commands.AddPermissionToRoleCommand;
import org.fennel.api.users.commands.CreateRoleCommand;
import org.fennel.api.users.commands.RemovePermissionFromRoleCommand;
import org.fennel.api.users.events.PermissionAddedToRoleEvent;
import org.fennel.api.users.events.PermissionRemovedFromRoleEvent;
import org.fennel.api.users.events.RoleCreatedEvent;

@Aggregate
public class Role implements Serializable {
  private static final long serialVersionUID = 2134161503548088614L;

  @AggregateIdentifier
  private RoleName                   roleName;
  private final List<PermissionName> permissions = new ArrayList<>();

  public Role() {
  }

  @CommandHandler
  public Role(final CreateRoleCommand command) {
    AggregateLifecycle.apply(RoleCreatedEvent.builder()
      .roleName(command.getRoleName())
      .build());
  }

  @CommandHandler
  public void handleAddPermissionCommand(final AddPermissionToRoleCommand command) {
    if (!permissions.contains(command.getPermissionName())) {
      AggregateLifecycle.apply(PermissionAddedToRoleEvent.builder()
        .roleName(command.getRoleName())
        .permissionName(command.getPermissionName())
        .build());
    }
  }

  @CommandHandler
  public void handleRemovePermissionCommand(final RemovePermissionFromRoleCommand command) {
    if (permissions.contains(command.getPermissionName())) {
      AggregateLifecycle.apply(PermissionRemovedFromRoleEvent.builder()
        .roleName(command.getRoleName())
        .permissionName(command.getPermissionName())
        .build());
    }
  }

  @EventSourcingHandler
  public void onRoleCreated(final RoleCreatedEvent event) {
    roleName = event.getRoleName();
  }

  @EventSourcingHandler
  public void onPermissionAdded(final PermissionAddedToRoleEvent event) {
    permissions.add(event.getPermissionName());
  }

  @EventSourcingHandler
  public void onPermissionRemoved(final PermissionRemovedFromRoleEvent event) {
    permissions.remove(event.getPermissionName());
  }
}
