package org.fennel.users.command;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import org.axonframework.test.matchers.Matchers;
import org.axonframework.test.saga.SagaTestFixture;
import org.fennel.services.User;
import org.fennel.services.UserCheck;
import org.fennel.users.api.user.CreateCommand;
import org.fennel.users.api.usercreationprocess.ConfirmEvent;
import org.fennel.users.api.usercreationprocess.ConfirmedCommand;
import org.fennel.users.api.usercreationprocess.CreatedEvent;
import org.fennel.users.api.usercreationprocess.FailCheckCommand;
import org.fennel.users.api.usercreationprocess.NewPinEvent;
import org.fennel.users.api.usercreationprocess.TerminatedEvent;
import org.fennel.users.command.UserCreationProcessSaga;
import org.fennel.users.api.usercreationprocess.DataCheckCommand;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public class UserCreationProcessSagaTests {
  private SagaTestFixture<UserCreationProcessSaga> fixture;
  private UserCheck                                userCheck;

  @Before
  public void setUp() {
    fixture = new SagaTestFixture<>(UserCreationProcessSaga.class);
    userCheck = Mockito.mock(UserCheck.class);
    fixture.registerResource(userCheck);
  }

  @Test
  public void scheduleDeletionOfUserIfUnconfirmedFor30Days() throws Exception {
    Mockito.when(userCheck.check(ArgumentMatchers.any(User.class)))
      .thenReturn(CompletableFuture.completedFuture(true));

    fixture
      .whenAggregate("1234").publishes(CreatedEvent.builder()
        .processId("1234")
        .pin("4321")
        .displayName("user")
        .username("username")
        .password("password")
        .build())
      .expectScheduledEvent(Duration.ofDays(30), TerminatedEvent.builder()
        .processId("1234")
        .build());
  }

  @Test
  public void validateUsername() throws Exception {

    Mockito
      .when(userCheck.check(User.builder()
        .displayName("user1")
        .username("username1")
        .password("password1")
        .processId("1")
        .build()))
      .thenReturn(CompletableFuture.completedFuture(true));
    Mockito
      .when(userCheck.check(User.builder()
        .displayName("user2")
        .username("username2")
        .password("password2")
        .processId("2")
        .build()))
      .thenReturn(CompletableFuture.completedFuture(true));

    fixture
      .givenAggregate("1").published(CreatedEvent.builder()
        .processId("1")
        .pin("1")
        .displayName("user1")
        .username("username1")
        .password("password1")
        .build())
      .whenAggregate("2").publishes(CreatedEvent.builder()
        .processId("2")
        .pin("2")
        .displayName("user2")
        .username("username2")
        .password("password2")
        .build())
      .expectDispatchedCommands(DataCheckCommand.builder()
        .processId("2")
        .build())
      .expectActiveSagas(2);
  }

  @Test
  public void validateUsernameDuplicate() throws Exception {

    Mockito
      .when(userCheck.check(User.builder()
        .displayName("user1")
        .username("username")
        .password("password1")
        .processId("1")
        .build()))
      .thenReturn(CompletableFuture.completedFuture(true));

    Mockito
      .when(userCheck.check(User.builder()
        .displayName("user2")
        .username("username")
        .password("password2")
        .processId("2")
        .build()))
      .thenReturn(CompletableFuture.completedFuture(false));

    fixture
      .givenAggregate("1").published(CreatedEvent.builder()
        .processId("1")
        .pin("1")
        .displayName("user1")
        .username("username")
        .password("password1")
        .build())
      .whenAggregate("2").publishes(CreatedEvent.builder()
        .processId("2")
        .pin("2")
        .displayName("user2")
        .username("username")
        .password("password2")
        .build())
      .expectDispatchedCommands(FailCheckCommand.builder()
        .processId("2")
        .build());
  }

  @Test
  public void confirmUser() throws Exception {
    Mockito.when(userCheck.check(ArgumentMatchers.any(User.class)))
      .thenReturn(CompletableFuture.supplyAsync(() -> true));

    fixture
      .givenAggregate("1234").published(CreatedEvent.builder()
        .processId("1234")
        .pin("4321")
        .displayName("user")
        .username("username")
        .password("password")
        .build())
      .whenAggregate("1234").publishes(ConfirmEvent.builder()
        .processId("1234")
        .pin("4321")
        .build())
      .expectDispatchedCommandsMatching(
        Matchers.payloadsMatching(Matchers.listWithAllOf(
          Matchers.equalTo(ConfirmedCommand.builder()
            .processId("1234")
            .build()),
          Matchers.equalTo(CreateCommand.builder()
            .userId(null)
            .displayName("user")
            .username("username")
            .password("password")
            .processId("1234")
            .build(), field -> !field.getName().equals("userId")))))
      .expectNoScheduledEvents()
      .expectActiveSagas(0);
  }

  @Test
  public void falsePinConfirmUser() throws Exception {
    Mockito.when(userCheck.check(ArgumentMatchers.any(User.class)))
      .thenReturn(CompletableFuture.completedFuture(true));

    fixture
      .givenAggregate("1234").published(CreatedEvent.builder()
        .processId("1234")
        .pin("4321")
        .displayName("user")
        .username("username")
        .password("password")
        .build())
      .whenAggregate("1234").publishes(ConfirmEvent.builder()
        .processId("1234")
        .pin("43211")
        .build())
      .expectActiveSagas(1);
  }

  @Test
  public void newPinConfirmUser() throws Exception {
    Mockito.when(userCheck.check(ArgumentMatchers.any(User.class)))
      .thenReturn(CompletableFuture.completedFuture(true));

    fixture
      .givenAggregate("1234").published(CreatedEvent.builder()
        .processId("1234")
        .pin("4321")
        .displayName("user")
        .username("username")
        .password("password")
        .build())
      .andThenAggregate("1234").published(NewPinEvent.builder()
        .processId("1234")
        .pin("asdf")
        .build())
      .whenAggregate("1234").publishes(ConfirmEvent.builder()
        .processId("1234")
        .pin("asdf")
        .build())
      .expectDispatchedCommandsMatching(
        Matchers.payloadsMatching(Matchers.listWithAllOf(
          Matchers.equalTo(ConfirmedCommand.builder()
            .processId("1234")
            .build()),
          Matchers.equalTo(CreateCommand.builder()
            .userId(null)
            .displayName("user")
            .username("username")
            .password("password")
            .processId("1234")
            .build(), field -> !field.getName().equals("userId")))))
      .expectNoScheduledEvents()
      .expectActiveSagas(0);
  }

  @Test
  public void forceTerminateSaga() throws Exception {
    Mockito.when(userCheck.check(ArgumentMatchers.any(User.class)))
      .thenReturn(CompletableFuture.completedFuture(true));

    fixture
      .givenAggregate("1234").published(CreatedEvent.builder()
        .processId("1234")
        .pin("4321")
        .displayName("user")
        .username("username")
        .password("password")
        .build())
      .whenPublishingA(TerminatedEvent.builder()
        .processId("1234")
        .build())
      .expectActiveSagas(0);
  }
}
