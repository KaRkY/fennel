package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.users.api.Password;
import org.fennel.users.api.UserId;
import org.fennel.users.api.UserPin;
import org.fennel.users.api.Username;
import org.fennel.users.api.commands.LockUserCommand;
import org.fennel.users.api.commands.UnlockUserCommand;
import org.fennel.users.api.events.ConfirmUserEvent;
import org.fennel.users.api.events.UserConfirmedEvent;
import org.fennel.users.api.events.UserCreatedEvent;
import org.fennel.users.api.events.UserLockedEvent;
import org.fennel.users.api.events.UserUnlockedEvent;
import org.fennel.users.commands.User;
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
        UserCreatedEvent.builder()
          .userId(UserId.of("1234"))
          .displayName("User 1")
          .username(Username.of("user1@gmail.com"))
          .password(Password.of("1234"))
          .pin(UserPin.of("1234567890"))
          .confirmed(false)
          .locked(false)
          .build())
      .when(LockUserCommand.builder()
        .userId(UserId.of("1234"))
        .build())
      .expectEvents(UserLockedEvent.builder()
        .userId(UserId.of("1234"))
        .build());
  }

  @Test
  public void lockUnlockedUser() throws Exception {
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
      .when(LockUserCommand.builder()
        .userId(UserId.of("1234"))
        .build())
      .expectEvents(UserLockedEvent.builder()
        .userId(UserId.of("1234"))
        .build());
  }

  @Test
  public void lockLockedUser() throws Exception {
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
      .when(LockUserCommand.builder()
        .userId(UserId.of("1234"))
        .build())
      .expectEvents();
  }

  @Test
  public void unlockLockedUser() throws Exception {
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
      .when(UnlockUserCommand.builder()
        .userId(UserId.of("1234"))
        .build())
      .expectEvents(UserUnlockedEvent.builder()
        .userId(UserId.of("1234"))
        .build());
  }

  @Test
  public void unlockUnlockedUser() throws Exception {
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
      .when(UnlockUserCommand.builder()
        .userId(UserId.of("1234"))
        .build())
      .expectEvents();
  }
}
