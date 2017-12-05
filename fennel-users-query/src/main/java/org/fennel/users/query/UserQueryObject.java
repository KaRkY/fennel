package org.fennel.users.query;

import org.fennel.api.users.UserId;
import org.fennel.api.users.Username;
import org.immutables.value.Value;

@Value.Immutable
public interface UserQueryObject {

  UserId userId();

  String displayName();

  Username username();

  boolean locked();
}
