package org.fennel.users.api;

import java.io.Serializable;
import java.security.SecureRandom;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class UserPin implements Serializable {
  private static final long         serialVersionUID = -6581008675888933469L;
  private static final SecureRandom RANDOM           = new SecureRandom();

  @NonNull
  private final String value;

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
