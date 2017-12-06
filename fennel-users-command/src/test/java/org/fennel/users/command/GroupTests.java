package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.api.users.GroupName;
import org.fennel.api.users.PermissionName;
import org.fennel.api.users.RoleName;
import org.fennel.api.users.commands.AddPermissionToGroupCommand;
import org.fennel.api.users.commands.AddRoleToGroupCommand;
import org.fennel.api.users.commands.CreateGroupCommand;
import org.fennel.api.users.commands.RemovePermissionFromGroupCommand;
import org.fennel.api.users.commands.RemoveRoleFromGroupCommand;
import org.fennel.api.users.events.GroupCreatedEvent;
import org.fennel.api.users.events.PermissionAddedToGroupEvent;
import org.fennel.api.users.events.PermissionRemovedFromGroupEvent;
import org.fennel.api.users.events.RoleAddedToGroupEvent;
import org.fennel.api.users.events.RoleRemovedFromGroupEvent;
import org.junit.Before;
import org.junit.Test;

public class GroupTests {
  private AggregateTestFixture<Group> fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture<>(Group.class);
  }

  @Test
  public void createGroup() throws Exception {
    fixture
      .given()
      .when(CreateGroupCommand.builder()
        .groupName(GroupName.of("root"))
        .build())
      .expectEvents(GroupCreatedEvent.builder()
        .groupName(GroupName.of("root"))
        .build());
  }

  @Test
  public void addRole() throws Exception {
    fixture
      .given(GroupCreatedEvent.builder()
        .groupName(GroupName.of("root"))
        .build())
      .when(AddRoleToGroupCommand.builder()
        .groupName(GroupName.of("root"))
        .roleName(RoleName.of("root"))
        .build())
      .expectEvents(RoleAddedToGroupEvent.builder()
        .groupName(GroupName.of("root"))
        .roleName(RoleName.of("root"))
        .build());
  }

  @Test
  public void addExistingRole() throws Exception {
    fixture
      .given(
        GroupCreatedEvent.builder()
          .groupName(GroupName.of("root"))
          .build(),
        RoleAddedToGroupEvent.builder()
          .groupName(GroupName.of("root"))
          .roleName(RoleName.of("root"))
          .build())
      .when(AddRoleToGroupCommand.builder()
        .groupName(GroupName.of("root"))
        .roleName(RoleName.of("root"))
        .build())
      .expectEvents();
  }

  @Test
  public void removeRole() throws Exception {
    fixture
      .given(
        GroupCreatedEvent.builder()
          .groupName(GroupName.of("root"))
          .build(),
        RoleAddedToGroupEvent.builder()
          .groupName(GroupName.of("root"))
          .roleName(RoleName.of("root"))
          .build())
      .when(RemoveRoleFromGroupCommand.builder()
        .groupName(GroupName.of("root"))
        .roleName(RoleName.of("root"))
        .build())
      .expectEvents(RoleRemovedFromGroupEvent.builder()
        .groupName(GroupName.of("root"))
        .roleName(RoleName.of("root"))
        .build());
  }

  @Test
  public void removeNonExistingRole() throws Exception {
    fixture
      .given(GroupCreatedEvent.builder()
        .groupName(GroupName.of("root"))
        .build())
      .when(RemoveRoleFromGroupCommand.builder()
        .groupName(GroupName.of("root"))
        .roleName(RoleName.of("root"))
        .build())
      .expectEvents();
  }

  @Test
  public void addPermission() throws Exception {
    fixture
      .given(GroupCreatedEvent.builder()
        .groupName(GroupName.of("root"))
        .build())
      .when(AddPermissionToGroupCommand.builder()
        .groupName(GroupName.of("root"))
        .permissionName(PermissionName.of("root"))
        .build())
      .expectEvents(PermissionAddedToGroupEvent.builder()
        .groupName(GroupName.of("root"))
        .permissionName(PermissionName.of("root"))
        .build());
  }

  @Test
  public void addExistingPermission() throws Exception {
    fixture
      .given(
        GroupCreatedEvent.builder()
          .groupName(GroupName.of("root"))
          .build(),
        PermissionAddedToGroupEvent.builder()
          .groupName(GroupName.of("root"))
          .permissionName(PermissionName.of("root"))
          .build())
      .when(AddPermissionToGroupCommand.builder()
        .groupName(GroupName.of("root"))
        .permissionName(PermissionName.of("root"))
        .build())
      .expectEvents();
  }

  @Test
  public void removePermission() throws Exception {
    fixture
      .given(
        GroupCreatedEvent.builder()
          .groupName(GroupName.of("root"))
          .build(),
        PermissionAddedToGroupEvent.builder()
          .groupName(GroupName.of("root"))
          .permissionName(PermissionName.of("root"))
          .build())
      .when(RemovePermissionFromGroupCommand.builder()
        .groupName(GroupName.of("root"))
        .permissionName(PermissionName.of("root"))
        .build())
      .expectEvents(PermissionRemovedFromGroupEvent.builder()
        .groupName(GroupName.of("root"))
        .permissionName(PermissionName.of("root"))
        .build());
  }

  @Test
  public void removeNonExistingPermission() throws Exception {
    fixture
      .given(GroupCreatedEvent.builder()
        .groupName(GroupName.of("root"))
        .build())
      .when(RemovePermissionFromGroupCommand.builder()
        .groupName(GroupName.of("root"))
        .permissionName(PermissionName.of("root"))
        .build())
      .expectEvents();
  }
}
