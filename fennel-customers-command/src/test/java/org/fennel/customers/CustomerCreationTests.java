package org.fennel.customers;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.customers.api.customer.CreateCommand;
import org.fennel.customers.api.customer.CreatedEvent;
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
      .when(CreateCommand.builder()
        .customerId("1234")
        .build())
      .expectEvents(CreatedEvent.builder()
        .customerId("1234")
        .build());
  }
}
