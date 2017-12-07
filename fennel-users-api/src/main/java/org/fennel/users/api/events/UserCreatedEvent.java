package org.fennel.users.api.events;

import org.fennel.users.api.Password;
import org.fennel.users.api.UserId;
import org.fennel.users.api.UserPin;
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
public class UserCreatedEvent {

  private final UserId   userId;
  private final String   displayName;
  private final Username username;
  private final Password password;
  private final UserPin  pin;
  private final boolean  confirmed;
  private final boolean  locked;

}
