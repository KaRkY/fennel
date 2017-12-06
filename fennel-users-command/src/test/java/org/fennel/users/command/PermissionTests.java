package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.api.users.PermissionName;
import org.fennel.api.users.commands.CreatePermissionCommand;
import org.fennel.api.users.events.PermissionCreatedEvent;
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
        .build())
      .expectEvents(PermissionCreatedEvent.builder()
        .permissionName(PermissionName.of("READ_USERS"))
        .build());
  }
}
