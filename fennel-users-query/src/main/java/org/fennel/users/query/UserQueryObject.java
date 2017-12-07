package org.fennel.users.query;

import org.fennel.users.api.UserId;
import org.fennel.users.api.Username;

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

  private UserId   userId;
  private String   displayName;
  private Username username;
  private boolean  confirmed;
  private boolean  locked;
}
