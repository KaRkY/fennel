package org.fennel.users.query;

import org.axonframework.eventhandling.EventHandler;
import org.fennel.api.users.events.UserCreationRequestedEvent;
import org.springframework.stereotype.Component;

@Component
public class UserQueryProcessor {

  @EventHandler
  public void onUserCreationRequestedEvent(final UserCreationRequestedEvent event) {
    System.out.println(event.getUserId());
  }
}
