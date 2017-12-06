package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.api.users.Password;
import org.fennel.api.users.UserId;
import org.fennel.api.users.UserPin;
import org.fennel.api.users.Username;
import org.fennel.api.users.commands.AuthorizeCommand;
import org.fennel.api.users.events.UserAuthorizationFailedEvent;
import org.fennel.api.users.events.UserAuthorizedEvent;
import org.fennel.api.users.events.UserConfirmationRequestedEvent;
import org.fennel.api.users.events.UserConfirmedEvent;
import org.fennel.api.users.events.UserCreatedEvent;
import org.fennel.api.users.events.UserCreationRequestedEvent;
import org.fennel.api.users.events.UserLockedEvent;
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
        UserCreationRequestedEvent.builder()
          .userId(UserId.of("1234"))
          .displayName("User 1")
          .username(Username.of("user1@gmail.com"))
          .password(Password.of("1234"))
          .pin(UserPin.of("1234567890"))
          .build(),
        UserConfirmationRequestedEvent.builder()
          .userId(UserId.of("1234"))
          .pin(UserPin.of("1234567890"))
          .build(),
        UserConfirmedEvent.builder()
          .userId(UserId.of("1234"))
          .build(),
        UserCreatedEvent.builder()
          .userId(UserId.of("1234"))
          .displayName("User 1")
          .username(Username.of("user1@gmail.com"))
          .password(Password.of("1234"))
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
        UserCreationRequestedEvent.builder()
          .userId(UserId.of("1234"))
          .displayName("User 1")
          .username(Username.of("user1@gmail.com"))
          .password(Password.of("1234"))
          .pin(UserPin.of("1234567890"))
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
        UserCreationRequestedEvent.builder()
          .userId(UserId.of("1234"))
          .displayName("User 1")
          .username(Username.of("user1@gmail.com"))
          .password(Password.of("1234"))
          .pin(UserPin.of("1234567890"))
          .build(),
        UserConfirmationRequestedEvent.builder()
          .userId(UserId.of("1234"))
          .pin(UserPin.of("1234567890"))
          .build(),
        UserConfirmedEvent.builder()
          .userId(UserId.of("1234"))
          .build(),
        UserCreatedEvent.builder()
          .userId(UserId.of("1234"))
          .displayName("User 1")
          .username(Username.of("user1@gmail.com"))
          .password(Password.of("1234"))
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
