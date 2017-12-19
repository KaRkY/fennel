package org.fennel.common.util;

import java.security.SecureRandom;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtil {
  private static final SecureRandom RANDOM = new SecureRandom();

  public static String random(final int length) {
    return random(length, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
  }

  public static String random(final int length, final String dictionary) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      builder.append(dictionary.charAt(RANDOM.nextInt(dictionary.length())));
    }

    return builder.toString();
  }
}
