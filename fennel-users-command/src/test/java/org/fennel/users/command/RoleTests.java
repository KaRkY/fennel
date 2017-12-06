package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.api.users.PermissionName;
import org.fennel.api.users.RoleName;
import org.fennel.api.users.commands.AddPermissionToRoleCommand;
import org.fennel.api.users.commands.CreateRoleCommand;
import org.fennel.api.users.commands.RemovePermissionFromRoleCommand;
import org.fennel.api.users.events.PermissionAddedToRoleEvent;
import org.fennel.api.users.events.PermissionRemovedFromRoleEvent;
import org.fennel.api.users.events.RoleCreatedEvent;
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
        .build())
      .expectEvents(RoleCreatedEvent.builder()
        .roleName(RoleName.of("root"))
        .build());
  }

  @Test
  public void addPermission() throws Exception {
    fixture
      .given(RoleCreatedEvent.builder()
        .roleName(RoleName.of("root"))
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
        .build())
      .when(RemovePermissionFromRoleCommand.builder()
        .roleName(RoleName.of("root"))
        .permissionName(PermissionName.of("root"))
        .build())
      .expectEvents();
  }
}
