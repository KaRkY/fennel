package org.fennel.customers;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.customers.api.CustomerId;
import org.fennel.customers.api.commands.CreateCustomerCommand;
import org.fennel.customers.api.events.CustomerCreatedEvent;
import org.fennel.customers.command.Customer;
import org.junit.Before;
import org.junit.Test;

public class CustomerCreationTests {
  private AggregateTestFixture<Customer> fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture<>(Customer.class);
  }

  @Test
  public void testCreateCustomer() throws Exception {
    fixture
      .given()
      .when(CreateCustomerCommand.builder()
        .customerId(CustomerId.of("1234"))
        .build())
      .expectEvents(CustomerCreatedEvent.builder()
        .customerId(CustomerId.of("1234"))
        .build());
  }
}
