package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.users.api.permission.CreateCommand;
import org.fennel.users.api.permission.CreatedEvent;
import org.fennel.users.command.Permission;
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
