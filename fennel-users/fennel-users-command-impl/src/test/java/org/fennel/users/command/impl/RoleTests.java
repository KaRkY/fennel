package org.fennel.users.command.impl;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.users.command.impl.Role;
import org.fennel.users.command.role.AddPermissionCommand;
import org.fennel.users.command.role.CreateCommand;
import org.fennel.users.command.role.CreatedEvent;
import org.fennel.users.command.role.PermissionAddedEvent;
import org.fennel.users.command.role.PermissionRemovedEvent;
import org.fennel.users.command.role.RemovePermissionCommand;
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
