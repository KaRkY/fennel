package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.users.api.commands.AddRoleToUserCommand;
import org.fennel.users.api.events.ConfirmUserEvent;
import org.fennel.users.api.events.RoleAddedToUserEvent;
import org.fennel.users.api.events.UserConfirmedEvent;
import org.fennel.users.api.events.UserCreatedEvent;
import org.fennel.users.commands.User;
import org.junit.Before;
import org.junit.Test;

public class UserRoleTests {
  private AggregateTestFixture<User> fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture<>(User.class);
  }

  @Test
  public void addRoleToUser() throws Exception {
    fixture
      .given(
        UserCreatedEvent.builder()
          .userId("1234")
          .displayName("User 1")
          .username("user1@gmail.com")
          .password("1234")
          .pin("1234567890")
          .confirmed(false)
          .locked(false)
          .build(),
        ConfirmUserEvent.builder()
          .userId("1234")
          .pin("1234567890")
          .build(),
        UserConfirmedEvent.builder()
          .userId("1234")
          .build())
      .when(AddRoleToUserCommand.builder()
        .userId("1234")
        .roleName("root")
        .build())
      .expectEvents(RoleAddedToUserEvent.builder()
        .userId("1234")
        .roleName("root")
        .build());
  }

}
