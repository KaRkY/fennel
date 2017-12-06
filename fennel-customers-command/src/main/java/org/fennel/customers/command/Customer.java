package org.fennel.customers.command;

import java.io.Serializable;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.fennel.api.customers.CustomerId;
import org.fennel.api.customers.commands.CreateCustomerCommand;
import org.fennel.api.customers.events.CustomerCreatedEvent;

@Aggregate
public class Customer implements Serializable {
  private static final long serialVersionUID = -1051646704866873459L;

  @AggregateIdentifier
  private CustomerId customerId;

  public Customer() {
  }

  @CommandHandler
  public Customer(final CreateCustomerCommand command) {
    AggregateLifecycle.apply(CustomerCreatedEvent.builder()
      .customerId(command.getCustomerId())
      .build());
  }

  @EventSourcingHandler
  public void onCustomerCreated(final CustomerCreatedEvent event) {
    customerId = event.getCustomerId();
  }
}
