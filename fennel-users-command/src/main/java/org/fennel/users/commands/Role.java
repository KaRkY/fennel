package org.fennel.users.commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.fennel.users.api.commands.AddPermissionToRoleCommand;
import org.fennel.users.api.commands.CreateRoleCommand;
import org.fennel.users.api.commands.RemovePermissionFromRoleCommand;
import org.fennel.users.api.events.PermissionAddedToRoleEvent;
import org.fennel.users.api.events.PermissionRemovedFromRoleEvent;
import org.fennel.users.api.events.RoleCreatedEvent;

@Aggregate
public class Role implements Serializable {
  private static final long serialVersionUID = 2134161503548088614L;

  @AggregateIdentifier
  private String             roleName;
  private final List<String> permissions = new ArrayList<>();

  public Role() {
  }

  @CommandHandler
  public Role(final CreateRoleCommand command) {
    AggregateLifecycle.apply(RoleCreatedEvent.builder()
      .roleName(command.getRoleName())
      .description(command.getDescription())
      .build());
  }

  @CommandHandler
  public void handle(final AddPermissionToRoleCommand command) {
    if (!permissions.contains(command.getPermissionName())) {
      AggregateLifecycle.apply(PermissionAddedToRoleEvent.builder()
        .roleName(command.getRoleName())
        .permissionName(command.getPermissionName())
        .build());
    }
  }

  @CommandHandler
  public void handle(final RemovePermissionFromRoleCommand command) {
    if (permissions.contains(command.getPermissionName())) {
      AggregateLifecycle.apply(PermissionRemovedFromRoleEvent.builder()
        .roleName(command.getRoleName())
        .permissionName(command.getPermissionName())
        .build());
    }
  }

  @EventSourcingHandler
  public void on(final RoleCreatedEvent event) {
    roleName = event.getRoleName();
  }

  @EventSourcingHandler
  public void on(final PermissionAddedToRoleEvent event) {
    permissions.add(event.getPermissionName());
  }

  @EventSourcingHandler
  public void on(final PermissionRemovedFromRoleEvent event) {
    permissions.remove(event.getPermissionName());
  }
}
