package org.fennel.users.command.usercreationprocess;

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

  private final String  processId;
  private final String  pin;
  private final String  displayName;
  private final String  username;
  private final String  password;
  private final boolean confirmed;

}
