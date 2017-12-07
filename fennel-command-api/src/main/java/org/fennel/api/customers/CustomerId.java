package org.fennel.api.customers;

import java.io.Serializable;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class CustomerId implements Serializable {
  private static final long serialVersionUID = 7411168692662174955L;

  @NonNull
  private final String value;

}
