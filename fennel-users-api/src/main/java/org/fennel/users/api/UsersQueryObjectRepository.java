package org.fennel.users.api;

import org.fennel.users.api.events.UserConfirmedEvent;
import org.fennel.users.api.events.UserCreatedEvent;

public interface UsersQueryObjectRepository {

  void insert(UserCreatedEvent event);

  void update(UserConfirmedEvent event);

  boolean containsUser(Username username);

  boolean containsAnyUser();

}
