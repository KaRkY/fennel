package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.users.api.user.CreateCommand;
import org.fennel.users.api.user.CreatedEvent;
import org.fennel.users.command.User;
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
      .when(CreateCommand.builder()
        .userId("1234")
        .displayName("User 1")
        .username("user1@gmail.com")
        .password("1234")
        .build())
      .expectEvents(CreatedEvent.builder()
        .userId("1234")
        .displayName("User 1")
        .username("user1@gmail.com")
        .password("1234")
        .locked(false)
        .build());
  }

}
