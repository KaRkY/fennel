package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.users.api.Password;
import org.fennel.users.api.RoleName;
import org.fennel.users.api.UserId;
import org.fennel.users.api.UserPin;
import org.fennel.users.api.Username;
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
          .userId(UserId.of("1234"))
          .displayName("User 1")
          .username(Username.of("user1@gmail.com"))
          .password(Password.of("1234"))
          .pin(UserPin.of("1234567890"))
          .confirmed(false)
          .locked(false)
          .build(),
        ConfirmUserEvent.builder()
          .userId(UserId.of("1234"))
          .pin(UserPin.of("1234567890"))
          .build(),
        UserConfirmedEvent.builder()
          .userId(UserId.of("1234"))
          .build())
      .when(AddRoleToUserCommand.builder()
        .userId(UserId.of("1234"))
        .roleName(RoleName.of("root"))
        .build())
      .expectEvents(RoleAddedToUserEvent.builder()
        .userId(UserId.of("1234"))
        .roleName(RoleName.of("root"))
        .build());
  }

}
