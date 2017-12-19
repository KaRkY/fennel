package org.fennel.users.command.impl;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import org.axonframework.test.saga.SagaTestFixture;
import org.fennel.common.services.IdGeneratorService;
import org.fennel.common.services.User;
import org.fennel.common.services.UserCheck;
import org.fennel.common.util.UserData;
import org.fennel.users.command.user.CreateCommand;
import org.fennel.users.command.user.UserType;
import org.fennel.users.command.usercreationprocess.ConfirmEvent;
import org.fennel.users.command.usercreationprocess.ConfirmedCommand;
import org.fennel.users.command.usercreationprocess.ConfirmedEvent;
import org.fennel.users.command.usercreationprocess.CreatedEvent;
import org.fennel.users.command.usercreationprocess.DataCheckCommand;
import org.fennel.users.command.usercreationprocess.FailCheckCommand;
import org.fennel.users.command.usercreationprocess.NewPinEvent;
import org.fennel.users.command.usercreationprocess.TerminatedEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public class UserCreationProcessSagaTests {
  private SagaTestFixture<UserCreationProcessSaga> fixture;
  private UserCheck                                userCheck;
  private IdGeneratorService                       idGenerator;

  @Before
  public void setUp() {
    fixture = new SagaTestFixture<>(UserCreationProcessSaga.class);
    userCheck = Mockito.mock(UserCheck.class);
    idGenerator = Mockito.mock(IdGeneratorService.class);
    fixture.registerResource(userCheck);
    fixture.registerResource(idGenerator);
  }

  @Test
  public void createConfirmedUser() throws Exception {
    Mockito.when(userCheck.check(ArgumentMatchers.any(User.class)))
    .thenReturn(CompletableFuture.completedFuture(true));

    fixture
    .whenAggregate("1234").publishes(CreatedEvent.builder()
      .processId("1234")
      .pin("4321")
      .displayName("user")
      .username("username")
      .password("password")
      .confirmed(true)
      .build())
    .expectDispatchedCommands(
      DataCheckCommand.builder()
      .processId("1234")
      .build(),
      ConfirmedCommand.builder()
      .processId("1234")
      .build())
    .expectNoScheduledEvents();
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
    .expectDispatchedCommands(ConfirmedCommand.builder()
      .processId("1234")
      .build())
    .expectNoScheduledEvents();
  }

  @Test
  public void createUser() throws Exception {
    Mockito.when(userCheck.check(ArgumentMatchers.any(User.class)))
    .thenReturn(CompletableFuture.supplyAsync(() -> true));

    Mockito.when(idGenerator.generate("user")).thenReturn("1234");

    fixture
    .givenAggregate("1234").published(CreatedEvent.builder()
      .processId("1234")
      .pin("4321")
      .displayName("user")
      .username("username")
      .password("password")
      .confirmed(true)
      .build())
    .whenAggregate("1234").publishes(ConfirmedEvent.builder()
      .processId("1234")
      .build())
    .expectDispatchedCommands(
      CreateCommand.builder()
      .userId("1234")
      .processId("1234")
      .displayName("user")
      .username("username")
      .password("password")
      .type(UserType.NORMAL)
      .userData(UserData.builder()
        .userId("1234")
        .build())
      .build())
    .expectNoScheduledEvents();
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
    .expectDispatchedCommands(ConfirmedCommand.builder()
      .processId("1234")
      .build())
    .expectNoScheduledEvents();
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
