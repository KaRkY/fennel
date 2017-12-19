package org.fennel.users.query.impl.user;

import org.axonframework.eventhandling.EventHandler;
import org.fennel.users.command.user.CreatedEvent;
import org.fennel.users.command.usercreationprocess.ConfirmedEvent;

public class Processor {

  private final Repository repository;

  public Processor(final Repository repository) {
    this.repository = repository;
  }

  @EventHandler
  public void on(final CreatedEvent event) {
    repository.insert(event);
  }

  @EventHandler
  public void on(final ConfirmedEvent event) {
    // repository.update(event);
  }
}
