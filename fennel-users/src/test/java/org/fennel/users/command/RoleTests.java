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
      .when(new CreateRoleCommand(RoleName.of("root")))
      .expectEvents(new RoleCreatedEvent(RoleName.of("root")));
  }

  @Test
  public void addPermission() throws Exception {
    fixture
      .given(new RoleCreatedEvent(RoleName.of("root")))
      .when(new AddPermissionToRoleCommand(RoleName.of("root"), PermissionName.of("root")))
      .expectEvents(new PermissionAddedToRoleEvent(RoleName.of("root"), PermissionName.of("root")));
  }

  @Test
  public void addExistingPermission() throws Exception {
    fixture
      .given(
        new RoleCreatedEvent(RoleName.of("root")),
        new PermissionAddedToRoleEvent(RoleName.of("root"), PermissionName.of("root")))
      .when(new AddPermissionToRoleCommand(RoleName.of("root"), PermissionName.of("root")))
      .expectEvents();
  }

  @Test
  public void removePermission() throws Exception {
    fixture
      .given(
        new RoleCreatedEvent(RoleName.of("root")),
        new PermissionAddedToRoleEvent(RoleName.of("root"), PermissionName.of("root")))
      .when(new RemovePermissionFromRoleCommand(RoleName.of("root"), PermissionName.of("root")))
      .expectEvents(new PermissionRemovedFromRoleEvent(RoleName.of("root"), PermissionName.of("root")));
  }

  @Test
  public void removeNonExistingPermission() throws Exception {
    fixture
      .given(new RoleCreatedEvent(RoleName.of("root")))
      .when(new RemovePermissionFromRoleCommand(RoleName.of("root"), PermissionName.of("root")))
      .expectEvents();
  }
}
