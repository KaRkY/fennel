package org.fennel.users;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.api.users.ConfirmUserCommand;
import org.fennel.api.users.CreateUserCommand;
import org.fennel.api.users.LockUserCommand;
import org.fennel.api.users.NewUserPin;
import org.fennel.api.users.NewUserPinCommand;
import org.fennel.api.users.UnlockUserCommand;
import org.fennel.api.users.UserConfirmationRequested;
import org.fennel.api.users.UserConfirmed;
import org.fennel.api.users.UserCreated;
import org.fennel.api.users.UserCreationRequested;
import org.fennel.api.users.UserId;
import org.fennel.api.users.UserLocked;
import org.fennel.api.users.UserUnlocked;
import org.fennel.users.command.User;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
  private AggregateTestFixture<User> fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture<>(User.class);
  }

  @Test
  public void testCreateUser() throws Exception {
    fixture
      .given()
      .when(new CreateUserCommand(UserId.of("1234"), "user 1", "user1@gmail.com", "1234", "1234567890"))
      .expectEvents(new UserCreationRequested(UserId.of("1234"), "user 1", "user1@gmail.com", "1234", "1234567890"));
  }

  @Test
  public void userConfirm() throws Exception {
    fixture
      .given(new UserCreationRequested(UserId.of("1234"), "user 1", "user1@gmail.com", "4321", "1234567890"))
      .when(new ConfirmUserCommand(UserId.of("1234"), "1234567890"))
      .expectEvents(
        new UserConfirmationRequested(UserId.of("1234"), "1234567890"),
        new UserConfirmed(UserId.of("1234")),
        new UserCreated(UserId.of("1234"), "user 1", "user1@gmail.com", "4321"))
      .expectReturnValue(true);
  }

  @Test
  public void userConfirmFalsePin() throws Exception {
    fixture
      .given(new UserCreationRequested(UserId.of("1234"), "user 1", "user1@gmail.com", "4321", "1234567890"))
      .when(new ConfirmUserCommand(UserId.of("1234"), "12345678901"))
      .expectEvents(new UserConfirmationRequested(UserId.of("1234"), "12345678901"))
      .expectReturnValue(false);
  }

  @Test
  public void userNewPinRequest() throws Exception {
    fixture
      .given(new UserCreationRequested(UserId.of("1234"), "user 1", "user1@gmail.com", "4321", "1234567890"))
      .when(new NewUserPinCommand(UserId.of("1234"), "12345678901"))
      .expectEvents(new NewUserPin(UserId.of("1234"), "12345678901"));
  }

  @Test
  public void userPinRequestedConfirm() throws Exception {
    fixture
      .given(
        new UserCreationRequested(UserId.of("1234"), "user 1", "user1@gmail.com", "4321", "1234567890"),
        new NewUserPin(UserId.of("1234"), "12345678901"))
      .when(new ConfirmUserCommand(UserId.of("1234"), "12345678901"))
      .expectEvents(
        new UserConfirmationRequested(UserId.of("1234"), "12345678901"),
        new UserConfirmed(UserId.of("1234")),
        new UserCreated(UserId.of("1234"), "user 1", "user1@gmail.com", "4321"))
      .expectReturnValue(true);
  }

  @Test
  public void userPinRequestedConfirmWithOldPin() throws Exception {
    fixture
      .given(
        new UserCreationRequested(UserId.of("1234"), "user 1", "user1@gmail.com", "4321", "1234567890"),
        new NewUserPin(UserId.of("1234"), "12345678901"))
      .when(new ConfirmUserCommand(UserId.of("1234"), "1234567890"))
      .expectEvents(new UserConfirmationRequested(UserId.of("1234"), "1234567890"))
      .expectReturnValue(false);
  }

  @Test
  public void lockUnlockedUser() throws Exception {
    fixture
      .given(
        new UserCreationRequested(UserId.of("1234"), "user 1", "user1@gmail.com", "4321", "1234567890"),
        new UserConfirmationRequested(UserId.of("1234"), "1234567890"),
        new UserConfirmed(UserId.of("1234")),
        new UserCreated(UserId.of("1234"), "user 1", "user1@gmail.com", "4321"))
      .when(new LockUserCommand(UserId.of("1234")))
      .expectEvents(new UserLocked(UserId.of("1234")));
  }

  @Test
  public void lockLockedUser() throws Exception {
    fixture
      .given(
        new UserCreationRequested(UserId.of("1234"), "user 1", "user1@gmail.com", "4321", "1234567890"),
        new UserConfirmationRequested(UserId.of("1234"), "1234567890"),
        new UserConfirmed(UserId.of("1234")),
        new UserCreated(UserId.of("1234"), "user 1", "user1@gmail.com", "4321"),
        new UserLocked(UserId.of("1234")))
      .when(new LockUserCommand(UserId.of("1234")))
      .expectEvents();
  }

  @Test
  public void unlockLockedUser() throws Exception {
    fixture
      .given(
        new UserCreationRequested(UserId.of("1234"), "user 1", "user1@gmail.com", "4321", "1234567890"),
        new UserConfirmationRequested(UserId.of("1234"), "1234567890"),
        new UserConfirmed(UserId.of("1234")),
        new UserCreated(UserId.of("1234"), "user 1", "user1@gmail.com", "4321"),
        new UserLocked(UserId.of("1234")))
      .when(new UnlockUserCommand(UserId.of("1234")))
      .expectEvents(new UserUnlocked(UserId.of("1234")));
  }

  @Test
  public void unlockUnlockedUser() throws Exception {
    fixture
      .given(
        new UserCreationRequested(UserId.of("1234"), "user 1", "user1@gmail.com", "4321", "1234567890"),
        new UserConfirmationRequested(UserId.of("1234"), "1234567890"),
        new UserConfirmed(UserId.of("1234")),
        new UserCreated(UserId.of("1234"), "user 1", "user1@gmail.com", "4321"))
      .when(new UnlockUserCommand(UserId.of("1234")))
      .expectEvents();
  }
}
