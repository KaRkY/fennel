package org.fennel.users.api.query;

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

  private String  userId;
  private String  displayName;
  private String  username;
  private boolean confirmed;
  private boolean locked;
}
