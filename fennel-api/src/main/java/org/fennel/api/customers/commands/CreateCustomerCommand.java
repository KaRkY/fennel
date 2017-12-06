package org.fennel.api.customers.commands;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.fennel.api.customers.CustomerId;

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

  @TargetAggregateIdentifier
  private final CustomerId customerId;
}
