package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.api.users.Password;
import org.fennel.api.users.UserId;
import org.fennel.api.users.UserPin;
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
        UserCreationRequestedEvent.builder()
          .userId(UserId.of("1234"))
          .displayName("User 1")
          .username(Username.of("user1@gmail.com"))
          .password(Password.of("1234"))
          .pin(UserPin.of("1234567890"))
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
      .when(LockUserCommand.builder()
        .userId(UserId.of("1234"))
        .build())
      .expectEvents();
  }

  @Test
  public void unlockLockedUser() throws Exception {
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
      .when(UnlockUserCommand.builder()
        .userId(UserId.of("1234"))
        .build())
      .expectEvents();
  }
}
