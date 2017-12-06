package org.fennel.api.users;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class UserId implements Serializable {
  private static final long serialVersionUID = -6581008675888933469L;
  private final String      identifier;

  private UserId(final String identifier) {
    super();
    this.identifier = identifier;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final UserId other = (UserId) o;

    return Objects.equals(identifier, other.identifier);

  }

  @Override
  public int hashCode() {
    return identifier.hashCode();
  }

  @Override
  public String toString() {
    return identifier;
  }

  public static UserId of(final String identifier) {
    return new UserId(Objects.requireNonNull(identifier, "identifier"));
  }

  public static UserId randomUUID() {
    return new UserId(UUID.randomUUID().toString());
  }
}
