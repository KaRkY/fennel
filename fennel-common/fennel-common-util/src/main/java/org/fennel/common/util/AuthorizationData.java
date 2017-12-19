package org.fennel.common.util;

import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorizationData {

  private final String      username;
  @Singular
  private final Set<String> groups;
  @Singular
  private final Set<String> permissions;
}
