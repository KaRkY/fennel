package org.fennel.api.users;

import java.io.Serializable;
import java.util.Objects;

public class Username implements Serializable {
  private static final long serialVersionUID = -673377202290865898L;
  private final String      username;

  private Username(final String username) {
    super();
    this.username = username;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Username other = (Username) o;

    return Objects.equals(username, other.username);

  }

  @Override
  public int hashCode() {
    return username.hashCode();
  }

  @Override
  public String toString() {
    return username;
  }

  public static Username of(final String username) {
    return new Username(Objects.requireNonNull(username, "username"));
  }
}
