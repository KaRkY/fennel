package org.fennel.api.users.events;

import org.fennel.api.users.UserId;
import org.fennel.api.users.UserPin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConfirmationRequestedEvent {

  private final UserId  userId;
  private final UserPin pin;

}
