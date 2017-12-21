package org.fennel.common.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Builder
@Wither
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UsersProperties {

  private final String publicGroupName;
  private final String internalGroupName;
  private final String systemUserId;
  private final String anonymousUserId;
  private final String adminGroupName;
}
