package org.fennel.users.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.fennel.users.api.role.AddPermissionCommand;
import org.fennel.users.api.role.CreateCommand;
import org.fennel.users.api.role.PermissionAddedEvent;
import org.fennel.users.api.role.PermissionRemovedEvent;
import org.fennel.users.api.role.RemovePermissionCommand;
import org.fennel.users.api.role.CreatedEvent;

@Aggregate
public class Role implements Serializable {
  private static final long serialVersionUID = 2134161503548088614L;

  @AggregateIdentifier
  private String             roleName;
  private final List<String> permissions = new ArrayList<>();

  public Role() {
  }

  @CommandHandler
  public Role(final CreateCommand command) {
    AggregateLifecycle.apply(CreatedEvent.builder()
      .roleName(command.getRoleName())
      .description(command.getDescription())
      .build());
  }

  @CommandHandler
  public void handle(final AddPermissionCommand command) {
    if (!permissions.contains(command.getPermissionName())) {
      AggregateLifecycle.apply(PermissionAddedEvent.builder()
        .roleName(command.getRoleName())
        .permissionName(command.getPermissionName())
        .build());
    }
  }

  @CommandHandler
  public void handle(final RemovePermissionCommand command) {
    if (permissions.contains(command.getPermissionName())) {
      AggregateLifecycle.apply(PermissionRemovedEvent.builder()
        .roleName(command.getRoleName())
        .permissionName(command.getPermissionName())
        .build());
    }
  }

  @EventSourcingHandler
  public void on(final CreatedEvent event) {
    roleName = event.getRoleName();
  }

  @EventSourcingHandler
  public void on(final PermissionAddedEvent event) {
    permissions.add(event.getPermissionName());
  }

  @EventSourcingHandler
  public void on(final PermissionRemovedEvent event) {
    permissions.remove(event.getPermissionName());
  }
}
