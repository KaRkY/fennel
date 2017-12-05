package org.fennel.users.command;

import java.io.Serializable;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.fennel.api.users.PermissionName;
import org.fennel.api.users.commands.CreatePermissionCommand;
import org.fennel.api.users.events.PermissionCreatedEvent;

@Aggregate
public class Permission implements Serializable {
  private static final long serialVersionUID = 780262963137038368L;

  @AggregateIdentifier
  private PermissionName permissionName;

  public Permission() {
  }

  @CommandHandler
  public Permission(final CreatePermissionCommand command) {
    AggregateLifecycle.apply(new PermissionCreatedEvent(command.getPermissionName()));
  }

  @EventSourcingHandler
  public void onPermissionCreated(final PermissionCreatedEvent event) {
    permissionName = event.getPermissionName();
  }

}
