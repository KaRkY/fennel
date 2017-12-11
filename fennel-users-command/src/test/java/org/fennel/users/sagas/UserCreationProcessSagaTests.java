package org.fennel.users.sagas;

import java.time.Duration;

import org.axonframework.test.matchers.Matchers;
import org.axonframework.test.saga.SagaTestFixture;
import org.fennel.users.api.commands.ConfirmedUserCommand;
import org.fennel.users.api.commands.CreateUserCommand;
import org.fennel.users.api.events.ConfirmUserEvent;
import org.fennel.users.api.events.NewUserPinEvent;
import org.fennel.users.api.events.UserCreationProcessCreated;
import org.fennel.users.api.events.UserCreationProcessTerminated;
import org.junit.Before;
import org.junit.Test;

public class UserCreationProcessSagaTests {
  private SagaTestFixture<UserCreationProcessSaga> fixture;

  @Before
  public void setUp() {
    fixture = new SagaTestFixture<>(UserCreationProcessSaga.class);
  }

  @Test
  public void scheduleDeletionOfUserIfUnconfirmedFor30Days() throws Exception {
    fixture
      .whenPublishingA(UserCreationProcessCreated.builder()
        .processId("1234")
        .pin("4321")
        .displayName("user")
        .username("username")
        .password("password")
        .build())
      .expectScheduledEvent(Duration.ofDays(30), UserCreationProcessTerminated.builder()
        .processId("1234")
        .build());
  }

  @Test
  public void confirmUser() throws Exception {
    fixture
      .givenAggregate("1234").published(UserCreationProcessCreated.builder()
        .processId("1234")
        .pin("4321")
        .displayName("user")
        .username("username")
        .password("password")
        .build())
      .whenAggregate("1234").publishes(ConfirmUserEvent.builder()
        .processId("1234")
        .pin("4321")
        .build())
      .expectDispatchedCommandsMatching(
        Matchers.payloadsMatching(Matchers.listWithAllOf(
          Matchers.equalTo(ConfirmedUserCommand.builder()
            .processId("1234")
            .build()),
          Matchers.equalTo(CreateUserCommand.builder()
            .userId(null)
            .displayName("user")
            .username("username")
            .password("password")
            .build(), field -> !field.getName().equals("userId")))))
      .expectNoScheduledEvents()
      .expectActiveSagas(0);
  }

  @Test
  public void falsePinConfirmUser() throws Exception {
    fixture
      .givenAggregate("1234").published(UserCreationProcessCreated.builder()
        .processId("1234")
        .pin("4321")
        .displayName("user")
        .username("username")
        .password("password")
        .build())
      .whenAggregate("1234").publishes(ConfirmUserEvent.builder()
        .processId("1234")
        .pin("43211")
        .build())
      .expectActiveSagas(1);
  }

  @Test
  public void newPinConfirmUser() throws Exception {
    fixture
      .givenAggregate("1234").published(UserCreationProcessCreated.builder()
        .processId("1234")
        .pin("4321")
        .displayName("user")
        .username("username")
        .password("password")
        .build())
      .andThenAggregate("1234").published(NewUserPinEvent.builder()
        .processId("1234")
        .pin("asdf")
        .build())
      .whenAggregate("1234").publishes(ConfirmUserEvent.builder()
        .processId("1234")
        .pin("asdf")
        .build())
      .expectDispatchedCommandsMatching(
        Matchers.payloadsMatching(Matchers.listWithAllOf(
          Matchers.equalTo(ConfirmedUserCommand.builder()
            .processId("1234")
            .build()),
          Matchers.equalTo(CreateUserCommand.builder()
            .userId(null)
            .displayName("user")
            .username("username")
            .password("password")
            .build(), field -> !field.getName().equals("userId")))))
      .expectNoScheduledEvents()
      .expectActiveSagas(0);
  }

  @Test
  public void forceTerminateSaga() throws Exception {
    fixture
      .whenPublishingA(UserCreationProcessTerminated.builder()
        .processId("1234")
        .build())
      .expectActiveSagas(0);
  }
}
