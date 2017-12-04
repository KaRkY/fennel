package org.fennel.api.users;

import java.io.Serializable;
import java.util.Objects;

public class PermissionName implements Serializable {
  private static final long serialVersionUID = 4589305410669002230L;
  private final String      permissionName;

  private PermissionName(final String permissionName) {
    super();
    this.permissionName = permissionName;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final PermissionName other = (PermissionName) o;

    return Objects.equals(permissionName, other.permissionName);

  }

  @Override
  public int hashCode() {
    return permissionName.hashCode();
  }

  @Override
  public String toString() {
    return permissionName;
  }

  public static PermissionName of(final String permissionName) {
    return new PermissionName(Objects.requireNonNull(permissionName, "permissionName"));
  }
}
