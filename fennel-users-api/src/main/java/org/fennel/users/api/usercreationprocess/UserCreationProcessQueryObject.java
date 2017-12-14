package org.fennel.users.api.usercreationprocess;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCreationProcessQueryObject {

  private String processId;
  private String displayName;
  private String username;
  private String password;
  private String state;
}
