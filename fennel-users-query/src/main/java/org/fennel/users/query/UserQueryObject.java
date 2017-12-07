package org.fennel.users.query;

import org.fennel.api.users.UserId;
import org.fennel.api.users.Username;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserQueryObject {

  private UserId userId;
  private String displayName;
  private Username username;
  private boolean locked;
}
