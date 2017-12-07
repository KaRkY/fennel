package org.fennel.users.api;

import java.io.Serializable;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class Password implements Serializable {
  private static final long serialVersionUID = -2021263306343470079L;

  @NonNull
  private final String value;

}
