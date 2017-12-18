package org.fennel.users.api.user;

import org.fennel.api.common.UserData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreatedEvent {

  @NonNull
  private final String  userId;
  @NonNull
  private final String  displayName;
  @NonNull
  private final String  username;
  private final String  password;
  private final String  processId;
  private final boolean locked;
  @NonNull
  private final UserType type;
  @NonNull
  private final UserData userData;

}
