package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.api.users.Password;
import org.fennel.api.users.UserId;
import org.fennel.api.users.Username;
import org.fennel.api.users.commands.LockUserCommand;
import org.fennel.api.users.commands.UnlockUserCommand;
import org.fennel.api.users.events.UserConfirmationRequestedEvent;
import org.fennel.api.users.events.UserConfirmedEvent;
import org.fennel.api.users.events.UserCreatedEvent;
import org.fennel.api.users.events.UserCreationRequestedEvent;
import org.fennel.api.users.events.UserLockedEvent;
import org.fennel.api.users.events.UserUnlockedEvent;
import org.junit.Before;
import org.junit.Test;

public class UserLockingTests {
  private AggregateTestFixture<User> fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture<>(User.class);
  }

  @Test
  public void lockUnconfirmedUser() throws Exception {
    fixture
      .given(
        new UserCreationRequestedEvent(
          UserId.of("1234"),
          "user 1",
          Username.of("user1@gmail.com"),
          Password.of("1234"),
          "1234567890"))
      .when(new LockUserCommand(UserId.of("1234")))
      .expectEvents(new UserLockedEvent(UserId.of("1234")));
  }

  @Test
  public void lockUnlockedUser() throws Exception {
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
      .when(new LockUserCommand(UserId.of("1234")))
      .expectEvents(new UserLockedEvent(UserId.of("1234")));
  }

  @Test
  public void lockLockedUser() throws Exception {
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
      .when(new LockUserCommand(UserId.of("1234")))
      .expectEvents();
  }

  @Test
  public void unlockLockedUser() throws Exception {
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
      .when(new UnlockUserCommand(UserId.of("1234")))
      .expectEvents(new UserUnlockedEvent(UserId.of("1234")));
  }

  @Test
  public void unlockUnlockedUser() throws Exception {
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
      .when(new UnlockUserCommand(UserId.of("1234")))
      .expectEvents();
  }
}
