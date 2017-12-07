package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.users.api.PermissionName;
import org.fennel.users.api.commands.CreatePermissionCommand;
import org.fennel.users.api.events.PermissionCreatedEvent;
import org.fennel.users.commands.Permission;
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
      .when(CreatePermissionCommand.builder()
        .permissionName(PermissionName.of("READ_USERS"))
        .description("description")
        .build())
      .expectEvents(PermissionCreatedEvent.builder()
        .permissionName(PermissionName.of("READ_USERS"))
        .description("description")
        .build());
  }
}
