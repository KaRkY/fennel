package org.fennel.users.sagas;

import java.time.Duration;

import org.axonframework.test.saga.SagaTestFixture;
import org.fennel.api.users.UserId;
import org.fennel.api.users.commands.LockUserCommand;
import org.fennel.api.users.events.UserAuthorizationFailedEvent;
import org.fennel.api.users.events.UserAuthorizedEvent;
import org.fennel.api.users.events.UserUnlockedEvent;
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
      .givenAPublished(new UserAuthorizationFailedEvent(UserId.of("1234"), true, false))
      .andThenTimeElapses(Duration.ofSeconds(30))
      .andThenAPublished(new UserAuthorizationFailedEvent(UserId.of("1234"), true, false))
      .andThenTimeElapses(Duration.ofSeconds(30))
      .andThenAPublished(new UserAuthorizationFailedEvent(UserId.of("1234"), true, false))
      .andThenTimeElapses(Duration.ofSeconds(30))
      .andThenAPublished(new UserAuthorizationFailedEvent(UserId.of("1234"), true, false))
      .andThenTimeElapses(Duration.ofSeconds(30))
      .whenPublishingA(new UserAuthorizationFailedEvent(UserId.of("1234"), true, false))
      .expectDispatchedCommands(new LockUserCommand(UserId.of("1234")))
      .expectScheduledEvent(Duration.ofMinutes(5), new UserUnlockedEvent(UserId.of("1234")));
  }

  @Test
  public void failedLoginNotLocked() throws Exception {
    fixture
      .givenAPublished(new UserAuthorizationFailedEvent(UserId.of("1234"), true, false))
      .andThenTimeElapses(Duration.ofMinutes(2))
      .andThenAPublished(new UserAuthorizationFailedEvent(UserId.of("1234"), true, false))
      .andThenTimeElapses(Duration.ofMinutes(2))
      .andThenAPublished(new UserAuthorizationFailedEvent(UserId.of("1234"), true, false))
      .andThenTimeElapses(Duration.ofMinutes(2))
      .andThenAPublished(new UserAuthorizationFailedEvent(UserId.of("1234"), true, false))
      .andThenTimeElapses(Duration.ofMinutes(2))
      .whenPublishingA(new UserAuthorizationFailedEvent(UserId.of("1234"), true, false))
      .expectNoDispatchedCommands()
      .expectNoScheduledEvents();
  }

  @Test
  public void successLogin() throws Exception {
    fixture
      .givenAPublished(new UserAuthorizationFailedEvent(UserId.of("1234"), true, false))
      .andThenTimeElapses(Duration.ofSeconds(30))
      .andThenAPublished(new UserAuthorizationFailedEvent(UserId.of("1234"), true, false))
      .andThenTimeElapses(Duration.ofSeconds(30))
      .andThenAPublished(new UserAuthorizedEvent(UserId.of("1234")))
      .andThenTimeElapses(Duration.ofSeconds(30))
      .andThenAPublished(new UserAuthorizationFailedEvent(UserId.of("1234"), true, false))
      .andThenTimeElapses(Duration.ofSeconds(30))
      .whenPublishingA(new UserAuthorizationFailedEvent(UserId.of("1234"), true, false))
      .expectNoDispatchedCommands()
      .expectNoScheduledEvents();
  }
}
