package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.users.api.Password;
import org.fennel.users.api.UserId;
import org.fennel.users.api.UserPin;
import org.fennel.users.api.Username;
import org.fennel.users.api.commands.AuthorizeCommand;
import org.fennel.users.api.events.ConfirmUserEvent;
import org.fennel.users.api.events.UserAuthorizationFailedEvent;
import org.fennel.users.api.events.UserAuthorizedEvent;
import org.fennel.users.api.events.UserConfirmedEvent;
import org.fennel.users.api.events.UserCreatedEvent;
import org.fennel.users.api.events.UserLockedEvent;
import org.fennel.users.commands.User;
import org.junit.Before;
import org.junit.Test;

public class UserAuthorizationTests {
  private AggregateTestFixture<User> fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture<>(User.class);
  }

  @Test
  public void authorizeUser() throws Exception {
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
      .when(AuthorizeCommand.builder()
        .userId(UserId.of("1234"))
        .username(Username.of("user1@gmail.com"))
        .password(Password.of("1234"))
        .build())
      .expectEvents(UserAuthorizedEvent.builder()
        .userId(UserId.of("1234"))
        .build())
      .expectReturnValue(true);
  }

  @Test
  public void authorizeUnconfirmedUser() throws Exception {
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
          .build())
      .when(AuthorizeCommand.builder()
        .userId(UserId.of("1234"))
        .username(Username.of("user1@gmail.com"))
        .password(Password.of("1234"))
        .build())
      .expectEvents(UserAuthorizationFailedEvent.builder()
        .userId(UserId.of("1234"))
        .confirmed(false)
        .locked(false)
        .build())
      .expectReturnValue(false);
  }

  @Test
  public void authorizeLockedUser() throws Exception {
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
          .build(),
        UserLockedEvent.builder()
          .userId(UserId.of("1234"))
          .build())
      .when(AuthorizeCommand.builder()
        .userId(UserId.of("1234"))
        .username(Username.of("user1@gmail.com"))
        .password(Password.of("1234"))
        .build())
      .expectEvents(UserAuthorizationFailedEvent.builder()
        .userId(UserId.of("1234"))
        .confirmed(true)
        .locked(true)
        .build())
      .expectReturnValue(false);
  }
}
