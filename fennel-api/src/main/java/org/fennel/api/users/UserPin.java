package org.fennel.api.users;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Objects;

public class UserPin implements Serializable {
  private static final long         serialVersionUID = -6581008675888933469L;
  private static final SecureRandom RANDOM           = new SecureRandom();
  private final String              value;

  private UserPin(final String value) {
    super();
    this.value = value;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final UserPin other = (UserPin) o;

    return Objects.equals(value, other.value);

  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return value;
  }

  public static UserPin of(final String value) {
    return new UserPin(Objects.requireNonNull(value, "value"));
  }

  public static UserPin random(final int length) {
    return random(length, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
  }

  public static UserPin random(final int length, final String dictionary) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      builder.append(dictionary.charAt(RANDOM.nextInt(dictionary.length())));
    }

    return new UserPin(builder.toString());
  }
}
