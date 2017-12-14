package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.users.api.group.AddPermissionCommand;
import org.fennel.users.api.group.AddRoleCommand;
import org.fennel.users.api.group.AddUserCommand;
import org.fennel.users.api.group.CreateCommand;
import org.fennel.users.api.group.CreatedEvent;
import org.fennel.users.api.group.PermissionAddedEvent;
import org.fennel.users.api.group.PermissionRemovedEvent;
import org.fennel.users.api.group.RemovePermissionCommand;
import org.fennel.users.api.group.RemoveRoleCommand;
import org.fennel.users.api.group.RoleAddedEvent;
import org.fennel.users.api.group.RoleRemovedEvent;
import org.fennel.users.api.group.UserAddedEvent;
import org.fennel.users.command.Group;
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
      .when(CreateCommand.builder()
        .groupName("root")
        .description("description")
        .build())
      .expectEvents(CreatedEvent.builder()
        .groupName("root")
        .description("description")
        .build());
  }

  @Test
  public void addRole() throws Exception {
    fixture
      .given(CreatedEvent.builder()
        .groupName("root")
        .description("description")
        .build())
      .when(AddRoleCommand.builder()
        .groupName("root")
        .roleName("root")
        .build())
      .expectEvents(RoleAddedEvent.builder()
        .groupName("root")
        .roleName("root")
        .build());
  }

  @Test
  public void addExistingRole() throws Exception {
    fixture
      .given(
        CreatedEvent.builder()
          .groupName("root")
          .description("description")
          .build(),
        RoleAddedEvent.builder()
          .groupName("root")
          .roleName("root")
          .build())
      .when(AddRoleCommand.builder()
        .groupName("root")
        .roleName("root")
        .build())
      .expectEvents();
  }

  @Test
  public void removeRole() throws Exception {
    fixture
      .given(
        CreatedEvent.builder()
          .groupName("root")
          .description("description")
          .build(),
        RoleAddedEvent.builder()
          .groupName("root")
          .roleName("root")
          .build())
      .when(RemoveRoleCommand.builder()
        .groupName("root")
        .roleName("root")
        .build())
      .expectEvents(RoleRemovedEvent.builder()
        .groupName("root")
        .roleName("root")
        .build());
  }

  @Test
  public void removeNonExistingRole() throws Exception {
    fixture
      .given(CreatedEvent.builder()
        .groupName("root")
        .description("description")
        .build())
      .when(RemoveRoleCommand.builder()
        .groupName("root")
        .roleName("root")
        .build())
      .expectEvents();
  }

  @Test
  public void addPermission() throws Exception {
    fixture
      .given(CreatedEvent.builder()
        .groupName("root")
        .description("description")
        .build())
      .when(AddPermissionCommand.builder()
        .groupName("root")
        .permissionName("root")
        .build())
      .expectEvents(PermissionAddedEvent.builder()
        .groupName("root")
        .permissionName("root")
        .build());
  }

  @Test
  public void addExistingPermission() throws Exception {
    fixture
      .given(
        CreatedEvent.builder()
          .groupName("root")
          .description("description")
          .build(),
        PermissionAddedEvent.builder()
          .groupName("root")
          .permissionName("root")
          .build())
      .when(AddPermissionCommand.builder()
        .groupName("root")
        .permissionName("root")
        .build())
      .expectEvents();
  }

  @Test
  public void removePermission() throws Exception {
    fixture
      .given(
        CreatedEvent.builder()
          .groupName("root")
          .description("description")
          .build(),
        PermissionAddedEvent.builder()
          .groupName("root")
          .permissionName("root")
          .build())
      .when(RemovePermissionCommand.builder()
        .groupName("root")
        .permissionName("root")
        .build())
      .expectEvents(PermissionRemovedEvent.builder()
        .groupName("root")
        .permissionName("root")
        .build());
  }

  @Test
  public void removeNonExistingPermission() throws Exception {
    fixture
      .given(CreatedEvent.builder()
        .groupName("root")
        .description("description")
        .build())
      .when(RemovePermissionCommand.builder()
        .groupName("root")
        .permissionName("root")
        .build())
      .expectEvents();
  }

  @Test
  public void addUserToGroup() throws Exception {
    fixture
      .given(CreatedEvent.builder()
        .groupName("root")
        .description("description")
        .build())
      .when(AddUserCommand.builder()
        .groupName("root")
        .userId("1234")
        .build())
      .expectEvents(UserAddedEvent.builder()
        .groupName("root")
        .userId("1234")
        .build());
  }
}
