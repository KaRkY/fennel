package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.users.api.role.AddPermissionCommand;
import org.fennel.users.api.role.CreateCommand;
import org.fennel.users.api.role.PermissionAddedEvent;
import org.fennel.users.api.role.PermissionRemovedEvent;
import org.fennel.users.api.role.RemovePermissionCommand;
import org.fennel.users.command.Role;
import org.fennel.users.api.role.CreatedEvent;
import org.junit.Before;
import org.junit.Test;

public class RoleTests {
  private AggregateTestFixture<Role> fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture<>(Role.class);
  }

  @Test
  public void createGroup() throws Exception {
    fixture
      .given()
      .when(CreateCommand.builder()
        .roleName("root")
        .description("description")
        .build())
      .expectEvents(CreatedEvent.builder()
        .roleName("root")
        .description("description")
        .build());
  }

  @Test
  public void addPermission() throws Exception {
    fixture
      .given(CreatedEvent.builder()
        .roleName("root")
        .description("description")
        .build())
      .when(AddPermissionCommand.builder()
        .roleName("root")
        .permissionName("root")
        .build())
      .expectEvents(PermissionAddedEvent.builder()
        .roleName("root")
        .permissionName("root")
        .build());
  }

  @Test
  public void addExistingPermission() throws Exception {
    fixture
      .given(
        CreatedEvent.builder()
          .roleName("root")
          .description("description")
          .build(),
        PermissionAddedEvent.builder()
          .roleName("root")
          .permissionName("root")
          .build())
      .when(AddPermissionCommand.builder()
        .roleName("root")
        .permissionName("root")
        .build())
      .expectEvents();
  }

  @Test
  public void removePermission() throws Exception {
    fixture
      .given(
        CreatedEvent.builder()
          .roleName("root")
          .description("description")
          .build(),
        PermissionAddedEvent.builder()
          .roleName("root")
          .permissionName("root")
          .build())
      .when(RemovePermissionCommand.builder()
        .roleName("root")
        .permissionName("root")
        .build())
      .expectEvents(PermissionRemovedEvent.builder()
        .roleName("root")
        .permissionName("root")
        .build());
  }

  @Test
  public void removeNonExistingPermission() throws Exception {
    fixture
      .given(CreatedEvent.builder()
        .roleName("root")
        .description("description")
        .build())
      .when(RemovePermissionCommand.builder()
        .roleName("root")
        .permissionName("root")
        .build())
      .expectEvents();
  }
}
