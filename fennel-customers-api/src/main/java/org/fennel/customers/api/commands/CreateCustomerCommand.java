package org.fennel.customers.api.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.customers.api.CustomerId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCustomerCommand {

  private final CustomerId customerId;

  @TargetAggregateIdentifier
  public String getTargetAggregateIdentifier() {
    return customerId.getValue();
  }
}
