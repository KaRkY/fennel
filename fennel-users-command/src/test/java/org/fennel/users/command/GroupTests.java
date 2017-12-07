package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.users.api.GroupName;
import org.fennel.users.api.PermissionName;
import org.fennel.users.api.RoleName;
import org.fennel.users.api.UserId;
import org.fennel.users.api.commands.AddPermissionToGroupCommand;
import org.fennel.users.api.commands.AddRoleToGroupCommand;
import org.fennel.users.api.commands.AddUserToGroupCommand;
import org.fennel.users.api.commands.CreateGroupCommand;
import org.fennel.users.api.commands.RemovePermissionFromGroupCommand;
import org.fennel.users.api.commands.RemoveRoleFromGroupCommand;
import org.fennel.users.api.events.GroupCreatedEvent;
import org.fennel.users.api.events.PermissionAddedToGroupEvent;
import org.fennel.users.api.events.PermissionRemovedFromGroupEvent;
import org.fennel.users.api.events.RoleAddedToGroupEvent;
import org.fennel.users.api.events.RoleRemovedFromGroupEvent;
import org.fennel.users.api.events.UserAddedToGroupEvent;
import org.fennel.users.commands.Group;
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
        .description("description")
        .build())
      .expectEvents(GroupCreatedEvent.builder()
        .groupName(GroupName.of("root"))
        .description("description")
        .build());
  }

  @Test
  public void addRole() throws Exception {
    fixture
      .given(GroupCreatedEvent.builder()
        .groupName(GroupName.of("root"))
        .description("description")
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
          .description("description")
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
          .description("description")
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
        .description("description")
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
        .description("description")
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
          .description("description")
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
          .description("description")
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
        .description("description")
        .build())
      .when(RemovePermissionFromGroupCommand.builder()
        .groupName(GroupName.of("root"))
        .permissionName(PermissionName.of("root"))
        .build())
      .expectEvents();
  }

  @Test
  public void addUserToGroup() throws Exception {
    fixture
      .given(GroupCreatedEvent.builder()
        .groupName(GroupName.of("root"))
        .description("description")
        .build())
      .when(AddUserToGroupCommand.builder()
        .groupName(GroupName.of("root"))
        .userId(UserId.of("1234"))
        .build())
      .expectEvents(UserAddedToGroupEvent.builder()
        .groupName(GroupName.of("root"))
        .userId(UserId.of("1234"))
        .build());
  }
}
