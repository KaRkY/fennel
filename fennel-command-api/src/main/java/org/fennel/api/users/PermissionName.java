package org.fennel.api.users;

import java.io.Serializable;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class PermissionName implements Serializable {
  private static final long serialVersionUID = 4589305410669002230L;

  @NonNull
  private final String value;

}
