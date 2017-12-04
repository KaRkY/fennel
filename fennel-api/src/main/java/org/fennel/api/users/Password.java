package org.fennel.api.users;

import java.io.Serializable;
import java.util.Objects;

public class Password implements Serializable {
  private static final long serialVersionUID = -2021263306343470079L;
  private final String      password;

  private Password(final String password) {
    super();
    this.password = password;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Password other = (Password) o;

    return Objects.equals(password, other.password);

  }

  @Override
  public int hashCode() {
    return password.hashCode();
  }

  @Override
  public String toString() {
    return password;
  }

  public static Password of(final String password) {
    return new Password(Objects.requireNonNull(password, "password"));
  }
}
