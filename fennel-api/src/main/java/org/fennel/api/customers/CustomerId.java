package org.fennel.api.customers;

import java.io.Serializable;
import java.util.Objects;

public class CustomerId implements Serializable {
  private static final long serialVersionUID = 7411168692662174955L;
  private final String      identifier;

  private CustomerId(final String identifier) {
    super();
    this.identifier = identifier;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final CustomerId other = (CustomerId) o;

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

  public static CustomerId of(final String identifier) {
    return new CustomerId(Objects.requireNonNull(identifier, "identifier"));
  }
}
