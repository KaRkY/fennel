package org.fennel.common.util;

import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserData {

  @NonNull
  private final String      userId;
  @NonNull
  private final String      defaultGroup;
  @Singular
  private final Set<String> groups;
  @Singular
  private final Set<String> permissions;

}
