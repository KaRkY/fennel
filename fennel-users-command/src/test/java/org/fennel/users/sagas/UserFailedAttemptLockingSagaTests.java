package org.fennel.users.sagas;

import java.time.Duration;

import org.axonframework.test.saga.SagaTestFixture;
import org.fennel.users.api.commands.LockUserCommand;
import org.fennel.users.api.events.UserAuthorizationFailedEvent;
import org.fennel.users.api.events.UserAuthorizedEvent;
import org.fennel.users.api.events.UserUnlockedEvent;
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
      .givenAPublished(UserAuthorizationFailedEvent.builder()
        .userId("1234")
        .confirmed(true)
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofSeconds(30))
      .andThenAPublished(UserAuthorizationFailedEvent.builder()
        .userId("1234")
        .confirmed(true)
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofSeconds(30))
      .andThenAPublished(UserAuthorizationFailedEvent.builder()
        .userId("1234")
        .confirmed(true)
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofSeconds(30))
      .andThenAPublished(UserAuthorizationFailedEvent.builder()
        .userId("1234")
        .confirmed(true)
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofSeconds(30))
      .whenPublishingA(UserAuthorizationFailedEvent.builder()
        .userId("1234")
        .confirmed(true)
        .locked(false)
        .build())
      .expectDispatchedCommands(LockUserCommand.builder()
        .userId("1234")
        .build())
      .expectScheduledEvent(Duration.ofMinutes(5), UserUnlockedEvent.builder()
        .userId("1234")
        .build());
  }

  @Test
  public void failedLoginNotLocked() throws Exception {
    fixture
      .givenAPublished(UserAuthorizationFailedEvent.builder()
        .userId("1234")
        .confirmed(true)
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofMinutes(2))
      .andThenAPublished(UserAuthorizationFailedEvent.builder()
        .userId("1234")
        .confirmed(true)
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofMinutes(2))
      .andThenAPublished(UserAuthorizationFailedEvent.builder()
        .userId("1234")
        .confirmed(true)
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofMinutes(2))
      .andThenAPublished(UserAuthorizationFailedEvent.builder()
        .userId("1234")
        .confirmed(true)
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofMinutes(2))
      .whenPublishingA(UserAuthorizationFailedEvent.builder()
        .userId("1234")
        .confirmed(true)
        .locked(false)
        .build())
      .expectNoDispatchedCommands()
      .expectNoScheduledEvents();
  }

  @Test
  public void successLogin() throws Exception {
    fixture
      .givenAPublished(UserAuthorizationFailedEvent.builder()
        .userId("1234")
        .confirmed(true)
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofSeconds(30))
      .andThenAPublished(UserAuthorizationFailedEvent.builder()
        .userId("1234")
        .confirmed(true)
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofSeconds(30))
      .andThenAPublished(UserAuthorizationFailedEvent.builder()
        .userId("1234")
        .confirmed(true)
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofSeconds(30))
      .andThenAPublished(UserAuthorizationFailedEvent.builder()
        .userId("1234")
        .confirmed(true)
        .locked(false)
        .build())
      .andThenTimeElapses(Duration.ofSeconds(30))
      .whenPublishingA(UserAuthorizedEvent.builder()
        .userId("1234")
        .build())
      .expectNoDispatchedCommands()
      .expectNoScheduledEvents();
  }
}
