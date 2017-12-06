package org.fennel.api.customers.events;

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
public class CustomerCreatedEvent {

  private final CustomerId customerId;
}
