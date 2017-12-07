package org.fennel.api.users;

import java.io.Serializable;
import java.util.UUID;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class UserId implements Serializable {
  private static final long serialVersionUID = -6581008675888933469L;

  @NonNull
  private final String value;

  public static UserId randomUUID() {
    return new UserId(UUID.randomUUID().toString());
  }
}
