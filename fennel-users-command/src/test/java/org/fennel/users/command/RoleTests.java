package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.users.api.PermissionName;
import org.fennel.users.api.RoleName;
import org.fennel.users.api.commands.AddPermissionToRoleCommand;
import org.fennel.users.api.commands.CreateRoleCommand;
import org.fennel.users.api.commands.RemovePermissionFromRoleCommand;
import org.fennel.users.api.events.PermissionAddedToRoleEvent;
import org.fennel.users.api.events.PermissionRemovedFromRoleEvent;
import org.fennel.users.api.events.RoleCreatedEvent;
import org.fennel.users.commands.Role;
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
      .when(CreateRoleCommand.builder()
        .roleName(RoleName.of("root"))
        .description("description")
        .build())
      .expectEvents(RoleCreatedEvent.builder()
        .roleName(RoleName.of("root"))
        .description("description")
        .build());
  }

  @Test
  public void addPermission() throws Exception {
    fixture
      .given(RoleCreatedEvent.builder()
        .roleName(RoleName.of("root"))
        .description("description")
        .build())
      .when(AddPermissionToRoleCommand.builder()
        .roleName(RoleName.of("root"))
        .permissionName(PermissionName.of("root"))
        .build())
      .expectEvents(PermissionAddedToRoleEvent.builder()
        .roleName(RoleName.of("root"))
        .permissionName(PermissionName.of("root"))
        .build());
  }

  @Test
  public void addExistingPermission() throws Exception {
    fixture
      .given(
        RoleCreatedEvent.builder()
          .roleName(RoleName.of("root"))
          .description("description")
          .build(),
        PermissionAddedToRoleEvent.builder()
          .roleName(RoleName.of("root"))
          .permissionName(PermissionName.of("root"))
          .build())
      .when(AddPermissionToRoleCommand.builder()
        .roleName(RoleName.of("root"))
        .permissionName(PermissionName.of("root"))
        .build())
      .expectEvents();
  }

  @Test
  public void removePermission() throws Exception {
    fixture
      .given(
        RoleCreatedEvent.builder()
          .roleName(RoleName.of("root"))
          .description("description")
          .build(),
        PermissionAddedToRoleEvent.builder()
          .roleName(RoleName.of("root"))
          .permissionName(PermissionName.of("root"))
          .build())
      .when(RemovePermissionFromRoleCommand.builder()
        .roleName(RoleName.of("root"))
        .permissionName(PermissionName.of("root"))
        .build())
      .expectEvents(PermissionRemovedFromRoleEvent.builder()
        .roleName(RoleName.of("root"))
        .permissionName(PermissionName.of("root"))
        .build());
  }

  @Test
  public void removeNonExistingPermission() throws Exception {
    fixture
      .given(RoleCreatedEvent.builder()
        .roleName(RoleName.of("root"))
        .description("description")
        .build())
      .when(RemovePermissionFromRoleCommand.builder()
        .roleName(RoleName.of("root"))
        .permissionName(PermissionName.of("root"))
        .build())
      .expectEvents();
  }
}
