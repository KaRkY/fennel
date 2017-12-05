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
      .when(new CreateGroupCommand(GroupName.of("root")))
      .expectEvents(new GroupCreatedEvent(GroupName.of("root")));
  }

  @Test
  public void addRole() throws Exception {
    fixture
      .given(new GroupCreatedEvent(GroupName.of("root")))
      .when(new AddRoleToGroupCommand(GroupName.of("root"), RoleName.of("root")))
      .expectEvents(new RoleAddedToGroupEvent(GroupName.of("root"), RoleName.of("root")));
  }

  @Test
  public void addExistingRole() throws Exception {
    fixture
      .given(
        new GroupCreatedEvent(GroupName.of("root")),
        new RoleAddedToGroupEvent(GroupName.of("root"), RoleName.of("root")))
      .when(new AddRoleToGroupCommand(GroupName.of("root"), RoleName.of("root")))
      .expectEvents();
  }

  @Test
  public void removeRole() throws Exception {
    fixture
      .given(
        new GroupCreatedEvent(GroupName.of("root")),
        new RoleAddedToGroupEvent(GroupName.of("root"), RoleName.of("root")))
      .when(new RemoveRoleFromGroupCommand(GroupName.of("root"), RoleName.of("root")))
      .expectEvents(new RoleRemovedFromGroupEvent(GroupName.of("root"), RoleName.of("root")));
  }

  @Test
  public void removeNonExistingRole() throws Exception {
    fixture
      .given(new GroupCreatedEvent(GroupName.of("root")))
      .when(new RemoveRoleFromGroupCommand(GroupName.of("root"), RoleName.of("root")))
      .expectEvents();
  }

  @Test
  public void addPermission() throws Exception {
    fixture
      .given(new GroupCreatedEvent(GroupName.of("root")))
      .when(new AddPermissionToGroupCommand(GroupName.of("root"), PermissionName.of("root")))
      .expectEvents(new PermissionAddedToGroupEvent(GroupName.of("root"), PermissionName.of("root")));
  }

  @Test
  public void addExistingPermission() throws Exception {
    fixture
      .given(
        new GroupCreatedEvent(GroupName.of("root")),
        new PermissionAddedToGroupEvent(GroupName.of("root"), PermissionName.of("root")))
      .when(new AddPermissionToGroupCommand(GroupName.of("root"), PermissionName.of("root")))
      .expectEvents();
  }

  @Test
  public void removePermission() throws Exception {
    fixture
      .given(
        new GroupCreatedEvent(GroupName.of("root")),
        new PermissionAddedToGroupEvent(GroupName.of("root"), PermissionName.of("root")))
      .when(new RemovePermissionFromGroupCommand(GroupName.of("root"), PermissionName.of("root")))
      .expectEvents(new PermissionRemovedFromGroupEvent(GroupName.of("root"), PermissionName.of("root")));
  }

  @Test
  public void removeNonExistingPermission() throws Exception {
    fixture
      .given(new GroupCreatedEvent(GroupName.of("root")))
      .when(new RemovePermissionFromGroupCommand(GroupName.of("root"), PermissionName.of("root")))
      .expectEvents();
  }
}
