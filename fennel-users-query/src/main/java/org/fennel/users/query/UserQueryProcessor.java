package org.fennel.users.query;

import org.axonframework.eventhandling.EventHandler;
import org.fennel.users.api.events.UserConfirmedEvent;
import org.fennel.users.api.events.UserCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class UserQueryProcessor {

  private final UsersQueryObjectRepository repository;

  public UserQueryProcessor(final UsersQueryObjectRepository repository) {
    this.repository = repository;
  }

  @EventHandler
  public void on(final UserCreatedEvent event) {
    repository.insert(event);
  }

  @EventHandler
  public void on(final UserConfirmedEvent event) {
    // repository.update(event);
  }
}
