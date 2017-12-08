package org.fennel.users.commands;

import java.io.Serializable;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.fennel.users.api.commands.CreatePermissionCommand;
import org.fennel.users.api.events.PermissionCreatedEvent;

@Aggregate
public class Permission implements Serializable {
  private static final long serialVersionUID = 780262963137038368L;

  @AggregateIdentifier
  private String permissionName;

  public Permission() {
  }

  @CommandHandler
  public Permission(final CreatePermissionCommand command) {
    AggregateLifecycle.apply(PermissionCreatedEvent.builder()
      .permissionName(command.getPermissionName())
      .description(command.getDescription())
      .build());
  }

  @EventSourcingHandler
  public void onPermissionCreated(final PermissionCreatedEvent event) {
    permissionName = event.getPermissionName();
  }

}
