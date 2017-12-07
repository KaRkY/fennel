package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.users.api.Password;
import org.fennel.users.api.UserId;
import org.fennel.users.api.UserPin;
import org.fennel.users.api.Username;
import org.fennel.users.api.commands.ConfirmUserCommand;
import org.fennel.users.api.commands.CreateUserCommand;
import org.fennel.users.api.commands.NewUserPinCommand;
import org.fennel.users.api.events.ConfirmUserEvent;
import org.fennel.users.api.events.NewUserPinEvent;
import org.fennel.users.api.events.UserConfirmedEvent;
import org.fennel.users.api.events.UserCreatedEvent;
import org.fennel.users.commands.User;
import org.junit.Before;
import org.junit.Test;

public class UserCreationTests {
  private AggregateTestFixture<User> fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture<>(User.class);
  }

  @Test
  public void createUser() throws Exception {
    fixture
      .given()
      .when(CreateUserCommand.builder()
        .userId(UserId.of("1234"))
        .displayName("User 1")
        .username(Username.of("user1@gmail.com"))
        .password(Password.of("1234"))
        .pin(UserPin.of("1234567890"))
        .build())
      .expectEvents(UserCreatedEvent.builder()
        .userId(UserId.of("1234"))
        .displayName("User 1")
        .username(Username.of("user1@gmail.com"))
        .password(Password.of("1234"))
        .pin(UserPin.of("1234567890"))
        .confirmed(false)
        .locked(false)
        .build());
  }

  @Test
  public void userConfirm() throws Exception {
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
      .when(ConfirmUserCommand.builder()
        .userId(UserId.of("1234"))
        .pin(UserPin.of("1234567890"))
        .build())
      .expectEvents(
        ConfirmUserEvent.builder()
          .userId(UserId.of("1234"))
          .pin(UserPin.of("1234567890"))
          .build(),
        UserConfirmedEvent.builder()
          .userId(UserId.of("1234"))
          .build())
      .expectReturnValue(true);
  }

  @Test
  public void userConfirmFalsePin() throws Exception {
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
      .when(ConfirmUserCommand.builder()
        .userId(UserId.of("1234"))
        .pin(UserPin.of("1234567891"))
        .build())
      .expectEvents(ConfirmUserEvent.builder()
        .userId(UserId.of("1234"))
        .pin(UserPin.of("1234567891"))
        .build())
      .expectReturnValue(false);
  }

  @Test
  public void userNewPinRequest() throws Exception {
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
      .when(NewUserPinCommand.builder()
        .userId(UserId.of("1234"))
        .pin(UserPin.of("1234567891"))
        .build())
      .expectEvents(NewUserPinEvent.builder()
        .userId(UserId.of("1234"))
        .pin(UserPin.of("1234567891"))
        .build());
  }

  @Test
  public void userPinRequestedConfirm() throws Exception {
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
        NewUserPinEvent.builder()
          .userId(UserId.of("1234"))
          .pin(UserPin.of("1234567891"))
          .build())
      .when(ConfirmUserCommand.builder()
        .userId(UserId.of("1234"))
        .pin(UserPin.of("1234567891"))
        .build())
      .expectEvents(
        ConfirmUserEvent.builder()
          .userId(UserId.of("1234"))
          .pin(UserPin.of("1234567891"))
          .build(),
        UserConfirmedEvent.builder()
          .userId(UserId.of("1234"))
          .build())
      .expectReturnValue(true);
  }

  @Test
  public void userPinRequestedConfirmWithOldPin() throws Exception {
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
        NewUserPinEvent.builder()
          .userId(UserId.of("1234"))
          .pin(UserPin.of("1234567891"))
          .build())
      .when(ConfirmUserCommand.builder()
        .userId(UserId.of("1234"))
        .pin(UserPin.of("1234567890"))
        .build())
      .expectEvents(ConfirmUserEvent.builder()
        .userId(UserId.of("1234"))
        .pin(UserPin.of("1234567890"))
        .build())
      .expectReturnValue(false);
  }

}
