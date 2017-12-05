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
      .when(new CreatePermissionCommand(PermissionName.of("READ_USERS")))
      .expectEvents(new PermissionCreatedEvent(PermissionName.of("READ_USERS")));
  }
}
