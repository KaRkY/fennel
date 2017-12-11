package org.fennel.users.api.events;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCreatedEvent {

  private final String  userId;
  private final String  displayName;
  private final String  username;
  private final String  password;
  private final boolean locked;

}
