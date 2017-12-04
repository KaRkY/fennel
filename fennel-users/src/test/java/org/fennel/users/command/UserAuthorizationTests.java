package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.api.users.Password;
import org.fennel.api.users.UserId;
import org.fennel.api.users.Username;
import org.fennel.api.users.commands.AuthorizeCommand;
import org.fennel.api.users.events.UserAuthorizationFailedEvent;
import org.fennel.api.users.events.UserAuthorizedEvent;
import org.fennel.api.users.events.UserConfirmationRequestedEvent;
import org.fennel.api.users.events.UserConfirmedEvent;
import org.fennel.api.users.events.UserCreatedEvent;
import org.fennel.api.users.events.UserCreationRequestedEvent;
import org.fennel.api.users.events.UserLockedEvent;
import org.fennel.users.command.User;
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
        new UserCreationRequestedEvent(
          UserId.of("1234"),
          "user 1",
          Username.of("user1@gmail.com"),
          Password.of("1234"),
          "1234567890"),
        new UserConfirmationRequestedEvent(UserId.of("1234"), "1234567890"),
        new UserConfirmedEvent(UserId.of("1234")),
        new UserCreatedEvent(
          UserId.of("1234"),
          "user 1",
          Username.of("user1@gmail.com"),
          Password.of("1234")))
      .when(new AuthorizeCommand(UserId.of("1234"), Username.of("user1@gmail.com"), Password.of("1234")))
      .expectEvents(new UserAuthorizedEvent(UserId.of("1234")))
      .expectReturnValue(true);
  }

  @Test
  public void authorizeUnconfirmedUser() throws Exception {
    fixture
      .given(
        new UserCreationRequestedEvent(
          UserId.of("1234"),
          "user 1",
          Username.of("user1@gmail.com"),
          Password.of("1234"),
          "1234567890"))
      .when(new AuthorizeCommand(UserId.of("1234"), Username.of("user1@gmail.com"), Password.of("1234")))
      .expectEvents(new UserAuthorizationFailedEvent(UserId.of("1234"), false, false))
      .expectReturnValue(false);
  }

  @Test
  public void authorizeLockedUser() throws Exception {
    fixture
      .given(
        new UserCreationRequestedEvent(
          UserId.of("1234"),
          "user 1",
          Username.of("user1@gmail.com"),
          Password.of("1234"),
          "1234567890"),
        new UserConfirmationRequestedEvent(UserId.of("1234"), "1234567890"),
        new UserConfirmedEvent(UserId.of("1234")),
        new UserCreatedEvent(
          UserId.of("1234"),
          "user 1",
          Username.of("user1@gmail.com"),
          Password.of("1234")),
        new UserLockedEvent(UserId.of("1234")))
      .when(new AuthorizeCommand(UserId.of("1234"), Username.of("user1@gmail.com"), Password.of("1234")))
      .expectEvents(new UserAuthorizationFailedEvent(UserId.of("1234"), true, true))
      .expectReturnValue(false);
  }
}
