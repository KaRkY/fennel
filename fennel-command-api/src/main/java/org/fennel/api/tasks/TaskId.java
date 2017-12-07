package org.fennel.api.tasks;

import java.io.Serializable;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class TaskId implements Serializable {
  private static final long serialVersionUID = 7411168692662174955L;

  @NonNull
  private final String value;

}
