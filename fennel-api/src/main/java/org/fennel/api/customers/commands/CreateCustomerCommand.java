package org.fennel.api.customers.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.api.customers.CustomerId;

public class CreateCustomerCommand {

  @TargetAggregateIdentifier
  private final CustomerId customerId;

  public CreateCustomerCommand(final CustomerId customerId) {
    this.customerId = customerId;
  }

  public CustomerId getCustomerId() {
    return customerId;
  }
}
