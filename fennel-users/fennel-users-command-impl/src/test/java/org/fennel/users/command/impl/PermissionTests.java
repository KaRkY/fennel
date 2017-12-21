package org.fennel.users.command.impl;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.users.command.permission.CreateCommand;
import org.fennel.users.command.permission.CreatedEvent;
import org.junit.Before;
import org.junit.Test;

public class PermissionTests {
  private AggregateTestFixture<Permission> fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture<>(Permission.class);
  }

  @Test
  public void createPermission() throws Exception {
    fixture
      .given()
      .when(CreateCommand.builder()
        .permissionName("READ_USERS")
        .description("description")
        .build())
      .expectEvents(CreatedEvent.builder()
        .permissionName("READ_USERS")
        .description("description")
        .build());
  }
}
