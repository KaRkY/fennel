package org.fennel.users.command.impl;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.common.util.UserData;
import org.fennel.users.command.impl.User;
import org.fennel.users.command.user.CreatedEvent;
import org.fennel.users.command.user.LockCommand;
import org.fennel.users.command.user.LockedEvent;
import org.fennel.users.command.user.UnlockCommand;
import org.fennel.users.command.user.UnlockedEvent;
import org.fennel.users.command.user.UserType;
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
      CreatedEvent.builder()
      .userId("1234")
      .displayName("User 1")
      .username("user1@gmail.com")
      .password("1234")
      .locked(false)
      .type(UserType.NORMAL)
      .userData(UserData.builder()
        .userId("1234")
        .build())
      .build())
    .when(LockCommand.builder()
      .userId("1234")
      .build())
    .expectEvents(LockedEvent.builder()
      .userId("1234")
      .build());
  }

  @Test
  public void lockUnlockedUser() throws Exception {
    fixture
    .given(
      CreatedEvent.builder()
      .userId("1234")
      .displayName("User 1")
      .username("user1@gmail.com")
      .password("1234")
      .locked(false)
      .type(UserType.NORMAL)
      .userData(UserData.builder()
        .userId("1234")
        .build())
      .build())
    .when(LockCommand.builder()
      .userId("1234")
      .build())
    .expectEvents(LockedEvent.builder()
      .userId("1234")
      .build());
  }

  @Test
  public void lockLockedUser() throws Exception {
    fixture
    .given(
      CreatedEvent.builder()
      .userId("1234")
      .displayName("User 1")
      .username("user1@gmail.com")
      .password("1234")
      .locked(false)
      .type(UserType.NORMAL)
      .userData(UserData.builder()
        .userId("1234")
        .build())
      .build(),
      LockedEvent.builder()
      .userId("1234")
      .build())
    .when(LockCommand.builder()
      .userId("1234")
      .build())
    .expectEvents();
  }

  @Test
  public void unlockLockedUser() throws Exception {
    fixture
    .given(
      CreatedEvent.builder()
      .userId("1234")
      .displayName("User 1")
      .username("user1@gmail.com")
      .password("1234")
      .locked(false)
      .type(UserType.NORMAL)
      .userData(UserData.builder()
        .userId("1234")
        .build())
      .build(),
      LockedEvent.builder()
      .userId("1234")
      .build())
    .when(UnlockCommand.builder()
      .userId("1234")
      .build())
    .expectEvents(UnlockedEvent.builder()
      .userId("1234")
      .build());
  }

  @Test
  public void unlockUnlockedUser() throws Exception {
    fixture
    .given(
      CreatedEvent.builder()
      .userId("1234")
      .displayName("User 1")
      .username("user1@gmail.com")
      .password("1234")
      .locked(false)
      .type(UserType.NORMAL)
      .userData(UserData.builder()
        .userId("1234")
        .build())
      .build())
    .when(UnlockCommand.builder()
      .userId("1234")
      .build())
    .expectEvents();
  }
}
