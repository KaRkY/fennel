package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.users.api.commands.AuthorizeCommand;
import org.fennel.users.api.events.UserAuthorizationFailedEvent;
import org.fennel.users.api.events.UserAuthorizedEvent;
import org.fennel.users.api.events.UserCreatedEvent;
import org.fennel.users.api.events.UserLockedEvent;
import org.fennel.users.commands.User;
import org.junit.Before;
import org.junit.Test;

public class UserAuthorizationTests {
  private AggregateTestFixture<User> fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture<>(User.class);
  }

  @Test
  public void authorizeUser() throws Exception {
    fixture
      .given(
        UserCreatedEvent.builder()
          .userId("1234")
          .displayName("User 1")
          .username("user1@gmail.com")
          .password("1234")
          .locked(false)
          .build())
      .when(AuthorizeCommand.builder()
        .userId("1234")
        .username("user1@gmail.com")
        .password("1234")
        .build())
      .expectEvents(UserAuthorizedEvent.builder()
        .userId("1234")
        .build())
      .expectReturnValue(true);
  }

  @Test
  public void authorizeLockedUser() throws Exception {
    fixture
      .given(
        UserCreatedEvent.builder()
          .userId("1234")
          .displayName("User 1")
          .username("user1@gmail.com")
          .password("1234")
          .locked(false)
          .build(),
        UserLockedEvent.builder()
          .userId("1234")
          .build())
      .when(AuthorizeCommand.builder()
        .userId("1234")
        .username("user1@gmail.com")
        .password("1234")
        .build())
      .expectEvents(UserAuthorizationFailedEvent.builder()
        .userId("1234")
        .locked(true)
        .build())
      .expectReturnValue(false);
  }
}
