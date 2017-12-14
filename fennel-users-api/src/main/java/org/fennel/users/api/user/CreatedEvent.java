package org.fennel.users.api.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreatedEvent {

  private final String  userId;
  private final String  displayName;
  private final String  username;
  private final String  password;
  private final String  processId;
  private final boolean locked;

}
