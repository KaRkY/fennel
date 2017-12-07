package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.api.users.Password;
import org.fennel.api.users.UserId;
import org.fennel.api.users.UserPin;
import org.fennel.api.users.Username;
import org.fennel.api.users.commands.ConfirmUserCommand;
import org.fennel.api.users.commands.CreateUserCommand;
import org.fennel.api.users.commands.NewUserPinCommand;
import org.fennel.api.users.events.NewUserPinEvent;
import org.fennel.api.users.events.UserConfirmationRequestedEvent;
import org.fennel.api.users.events.UserConfirmedEvent;
import org.fennel.api.users.events.UserCreatedEvent;
import org.fennel.api.users.events.UserCreationRequestedEvent;
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
      .expectEvents(UserCreationRequestedEvent.builder()
        .userId(UserId.of("1234"))
        .displayName("User 1")
        .username(Username.of("user1@gmail.com"))
        .password(Password.of("1234"))
        .pin(UserPin.of("1234567890"))
        .build());
  }

  @Test
  public void userConfirm() throws Exception {
    fixture
      .given(
        UserCreationRequestedEvent.builder()
          .userId(UserId.of("1234"))
          .displayName("User 1")
          .username(Username.of("user1@gmail.com"))
          .password(Password.of("1234"))
          .pin(UserPin.of("1234567890"))
          .build())
      .when(ConfirmUserCommand.builder()
        .userId(UserId.of("1234"))
        .pin(UserPin.of("1234567890"))
        .build())
      .expectEvents(
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
      .expectReturnValue(true);
  }

  @Test
  public void userConfirmFalsePin() throws Exception {
    fixture
      .given(
        UserCreationRequestedEvent.builder()
          .userId(UserId.of("1234"))
          .displayName("User 1")
          .username(Username.of("user1@gmail.com"))
          .password(Password.of("1234"))
          .pin(UserPin.of("1234567890"))
          .build())
      .when(ConfirmUserCommand.builder()
        .userId(UserId.of("1234"))
        .pin(UserPin.of("1234567891"))
        .build())
      .expectEvents(UserConfirmationRequestedEvent.builder()
        .userId(UserId.of("1234"))
        .pin(UserPin.of("1234567891"))
        .build())
      .expectReturnValue(false);
  }

  @Test
  public void userNewPinRequest() throws Exception {
    fixture
      .given(
        UserCreationRequestedEvent.builder()
          .userId(UserId.of("1234"))
          .displayName("User 1")
          .username(Username.of("user1@gmail.com"))
          .password(Password.of("1234"))
          .pin(UserPin.of("1234567890"))
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
        UserCreationRequestedEvent.builder()
          .userId(UserId.of("1234"))
          .displayName("User 1")
          .username(Username.of("user1@gmail.com"))
          .password(Password.of("1234"))
          .pin(UserPin.of("1234567890"))
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
        UserConfirmationRequestedEvent.builder()
          .userId(UserId.of("1234"))
          .pin(UserPin.of("1234567891"))
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
      .expectReturnValue(true);
  }

  @Test
  public void userPinRequestedConfirmWithOldPin() throws Exception {
    fixture
      .given(
        UserCreationRequestedEvent.builder()
          .userId(UserId.of("1234"))
          .displayName("User 1")
          .username(Username.of("user1@gmail.com"))
          .password(Password.of("1234"))
          .pin(UserPin.of("1234567890"))
          .build(),
        NewUserPinEvent.builder()
          .userId(UserId.of("1234"))
          .pin(UserPin.of("1234567891"))
          .build())
      .when(ConfirmUserCommand.builder()
        .userId(UserId.of("1234"))
        .pin(UserPin.of("1234567890"))
        .build())
      .expectEvents(UserConfirmationRequestedEvent.builder()
        .userId(UserId.of("1234"))
        .pin(UserPin.of("1234567890"))
        .build())
      .expectReturnValue(false);
  }

}
