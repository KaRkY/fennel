package org.fennel.api.users;

import java.io.Serializable;
import java.util.Objects;

public class RoleName implements Serializable {
  private static final long serialVersionUID = 4589305410669002230L;
  private final String      roleName;

  private RoleName(final String roleName) {
    super();
    this.roleName = roleName;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final RoleName other = (RoleName) o;

    return Objects.equals(roleName, other.roleName);

  }

  @Override
  public int hashCode() {
    return roleName.hashCode();
  }

  @Override
  public String toString() {
    return roleName;
  }

  public static RoleName of(final String roleName) {
    return new RoleName(Objects.requireNonNull(roleName, "roleName"));
  }
}
