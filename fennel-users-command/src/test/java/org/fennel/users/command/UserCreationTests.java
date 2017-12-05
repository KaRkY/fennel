package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.api.users.Password;
import org.fennel.api.users.UserId;
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
      .when(new CreateUserCommand(
        UserId.of("1234"),
        "user 1",
        Username.of("user1@gmail.com"),
        Password.of("1234"),
        "1234567890"))
      .expectEvents(new UserCreationRequestedEvent(
        UserId.of("1234"),
        "user 1",
        Username.of("user1@gmail.com"),
        Password.of("1234"),
        "1234567890"));
  }

  @Test
  public void userConfirm() throws Exception {
    fixture
      .given(
        new UserCreationRequestedEvent(
          UserId.of("1234"),
          "user 1",
          Username.of("user1@gmail.com"),
          Password.of("1234"),
          "1234567890"))
      .when(new ConfirmUserCommand(UserId.of("1234"), "1234567890"))
      .expectEvents(
        new UserConfirmationRequestedEvent(UserId.of("1234"), "1234567890"),
        new UserConfirmedEvent(UserId.of("1234")),
        new UserCreatedEvent(
          UserId.of("1234"),
          "user 1",
          Username.of("user1@gmail.com"),
          Password.of("1234")))
      .expectReturnValue(true);
  }

  @Test
  public void userConfirmFalsePin() throws Exception {
    fixture
      .given(
        new UserCreationRequestedEvent(
          UserId.of("1234"),
          "user 1",
          Username.of("user1@gmail.com"),
          Password.of("1234"),
          "1234567890"))
      .when(new ConfirmUserCommand(UserId.of("1234"), "12345678901"))
      .expectEvents(new UserConfirmationRequestedEvent(UserId.of("1234"), "12345678901"))
      .expectReturnValue(false);
  }

  @Test
  public void userNewPinRequest() throws Exception {
    fixture
      .given(
        new UserCreationRequestedEvent(
          UserId.of("1234"),
          "user 1",
          Username.of("user1@gmail.com"),
          Password.of("1234"),
          "1234567890"))
      .when(new NewUserPinCommand(UserId.of("1234"), "12345678901"))
      .expectEvents(new NewUserPinEvent(UserId.of("1234"), "12345678901"));
  }

  @Test
  public void userPinRequestedConfirm() throws Exception {
    fixture
      .given(
        new UserCreationRequestedEvent(
          UserId.of("1234"),
          "user 1",
          Username.of("user1@gmail.com"),
          Password.of("1234"),
          "1234567890"),
        new NewUserPinEvent(UserId.of("1234"), "12345678901"))
      .when(new ConfirmUserCommand(UserId.of("1234"), "12345678901"))
      .expectEvents(
        new UserConfirmationRequestedEvent(UserId.of("1234"), "12345678901"),
        new UserConfirmedEvent(UserId.of("1234")),
        new UserCreatedEvent(
          UserId.of("1234"),
          "user 1",
          Username.of("user1@gmail.com"),
          Password.of("1234")))
      .expectReturnValue(true);
  }

  @Test
  public void userPinRequestedConfirmWithOldPin() throws Exception {
    fixture
      .given(
        new UserCreationRequestedEvent(
          UserId.of("1234"),
          "user 1",
          Username.of("user1@gmail.com"),
          Password.of("1234"),
          "1234567890"),
        new NewUserPinEvent(UserId.of("1234"), "12345678901"))
      .when(new ConfirmUserCommand(UserId.of("1234"), "1234567890"))
      .expectEvents(new UserConfirmationRequestedEvent(UserId.of("1234"), "1234567890"))
      .expectReturnValue(false);
  }

}
