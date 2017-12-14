package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.users.api.user.AddRoleCommand;
import org.fennel.users.api.user.RoleAddedEvent;
import org.fennel.users.command.User;
import org.fennel.users.api.user.CreatedEvent;
import org.junit.Before;
import org.junit.Test;

public class UserRoleTests {
  private AggregateTestFixture<User> fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture<>(User.class);
  }

  @Test
  public void addRoleToUser() throws Exception {
    fixture
      .given(
        CreatedEvent.builder()
          .userId("1234")
          .displayName("User 1")
          .username("user1@gmail.com")
          .password("1234")
          .locked(false)
          .build())
      .when(AddRoleCommand.builder()
        .userId("1234")
        .roleName("root")
        .build())
      .expectEvents(RoleAddedEvent.builder()
        .userId("1234")
        .roleName("root")
        .build());
  }

}
