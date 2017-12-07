package org.fennel.users.api;

import java.io.Serializable;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class Username implements Serializable {
  private static final long serialVersionUID = -673377202290865898L;

  @NonNull
  private final String value;

}
