package org.fennel.users.command.impl;

import java.time.Duration;

import org.axonframework.test.saga.SagaTestFixture;
import org.fennel.users.command.user.AuthorizationFailedEvent;
import org.fennel.users.command.user.AuthorizedEvent;
import org.fennel.users.command.user.LockCommand;
import org.fennel.users.command.user.UnlockedEvent;
import org.junit.Before;
import org.junit.Test;

public class UserFailedAttemptLockingSagaTests {
  private SagaTestFixture<UserFailedAttemptLockingSaga> fixture;

  @Before
  public void setUp() {
    fixture = new SagaTestFixture<>(UserFailedAttemptLockingSaga.class);
  }

  @Test
  public void failLoginLock() throws Exception {
    fixture
      .givenAPublished(AuthorizationFailedEvent.builder()
        .userId("1234")
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofSeconds(30))
      .andThenAPublished(AuthorizationFailedEvent.builder()
        .userId("1234")
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofSeconds(30))
      .andThenAPublished(AuthorizationFailedEvent.builder()
        .userId("1234")
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofSeconds(30))
      .andThenAPublished(AuthorizationFailedEvent.builder()
        .userId("1234")
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofSeconds(30))
      .whenPublishingA(AuthorizationFailedEvent.builder()
        .userId("1234")
        .locked(false)
        .build())
      .expectDispatchedCommands(LockCommand.builder()
        .userId("1234")
        .build())
      .expectScheduledEvent(Duration.ofMinutes(5), UnlockedEvent.builder()
        .userId("1234")
        .build());
  }

  @Test
  public void failedLoginNotLocked() throws Exception {
    fixture
      .givenAPublished(AuthorizationFailedEvent.builder()
        .userId("1234")
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofMinutes(2))
      .andThenAPublished(AuthorizationFailedEvent.builder()
        .userId("1234")
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofMinutes(2))
      .andThenAPublished(AuthorizationFailedEvent.builder()
        .userId("1234")
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofMinutes(2))
      .andThenAPublished(AuthorizationFailedEvent.builder()
        .userId("1234")
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofMinutes(2))
      .whenPublishingA(AuthorizationFailedEvent.builder()
        .userId("1234")
        .locked(false)
        .build())
      .expectNoDispatchedCommands()
      .expectNoScheduledEvents();
  }

  @Test
  public void successLogin() throws Exception {
    fixture
      .givenAPublished(AuthorizationFailedEvent.builder()
        .userId("1234")
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofSeconds(30))
      .andThenAPublished(AuthorizationFailedEvent.builder()
        .userId("1234")
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofSeconds(30))
      .andThenAPublished(AuthorizationFailedEvent.builder()
        .userId("1234")
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofSeconds(30))
      .andThenAPublished(AuthorizationFailedEvent.builder()
        .userId("1234")
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofSeconds(30))
      .whenPublishingA(AuthorizedEvent.builder()
        .userId("1234")
        .build())
      .expectNoDispatchedCommands()
      .expectNoScheduledEvents();
  }
}
