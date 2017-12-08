package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
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
          .userId("1234")
          .displayName("User 1")
          .username("user1@gmail.com")
          .password("1234")
          .pin("1234567890")
          .confirmed(false)
          .locked(false)
          .build())
      .when(LockUserCommand.builder()
        .userId("1234")
        .build())
      .expectEvents(UserLockedEvent.builder()
        .userId("1234")
        .build());
  }

  @Test
  public void lockUnlockedUser() throws Exception {
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
      .when(LockUserCommand.builder()
        .userId("1234")
        .build())
      .expectEvents(UserLockedEvent.builder()
        .userId("1234")
        .build());
  }

  @Test
  public void lockLockedUser() throws Exception {
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
          .build(),
        UserLockedEvent.builder()
          .userId("1234")
          .build())
      .when(LockUserCommand.builder()
        .userId("1234")
        .build())
      .expectEvents();
  }

  @Test
  public void unlockLockedUser() throws Exception {
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
          .build(),
        UserLockedEvent.builder()
          .userId("1234")
          .build())
      .when(UnlockUserCommand.builder()
        .userId("1234")
        .build())
      .expectEvents(UserUnlockedEvent.builder()
        .userId("1234")
        .build());
  }

  @Test
  public void unlockUnlockedUser() throws Exception {
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
      .when(UnlockUserCommand.builder()
        .userId("1234")
        .build())
      .expectEvents();
  }
}
