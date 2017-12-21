package org.fennel.users.command.impl;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.common.util.UserData;
import org.fennel.users.command.user.AuthorizationFailedEvent;
import org.fennel.users.command.user.AuthorizeCommand;
import org.fennel.users.command.user.AuthorizedEvent;
import org.fennel.users.command.user.CreatedEvent;
import org.fennel.users.command.user.FailAuthorizationCommand;
import org.fennel.users.command.user.UserType;
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
        CreatedEvent.builder()
          .userId("1234")
          .displayName("User 1")
          .username("user1@gmail.com")
          .password("1234")
          .locked(false)
          .type(UserType.NORMAL)
          .userData(UserData.builder()
            .userId("1234")
            .build())
          .build())
      .when(AuthorizeCommand.builder()
        .userId("1234")
        .username("user1@gmail.com")
        .password("1234")
        .build())
      .expectEvents(AuthorizedEvent.builder()
        .userId("1234")
        .build());
  }

  @Test
  public void failAuthorizeUser() throws Exception {
    fixture
      .given(
        CreatedEvent.builder()
          .userId("1234")
          .displayName("User 1")
          .username("user1@gmail.com")
          .password("1234")
          .locked(false)
          .type(UserType.NORMAL)
          .userData(UserData.builder()
            .userId("1234")
            .build())
          .build())
      .when(FailAuthorizationCommand.builder()
        .userId("1234")
        .build())
      .expectEvents(AuthorizationFailedEvent.builder()
        .userId("1234")
        .build());
  }
}
