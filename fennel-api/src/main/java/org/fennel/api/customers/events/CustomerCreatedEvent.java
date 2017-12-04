package org.fennel.api.customers.events;

import org.fennel.api.customers.CustomerId;

public class CustomerCreatedEvent {

  private final CustomerId customerId;

  public CustomerCreatedEvent(final CustomerId customerId) {
    this.customerId = customerId;
  }

  public CustomerId getCustomerId() {
    return customerId;
  }
}
