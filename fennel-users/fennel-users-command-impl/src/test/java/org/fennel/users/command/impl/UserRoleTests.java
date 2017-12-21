package org.fennel.users.command.impl;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.common.util.UserData;
import org.fennel.users.command.user.AddRoleCommand;
import org.fennel.users.command.user.CreatedEvent;
import org.fennel.users.command.user.RoleAddedEvent;
import org.fennel.users.command.user.UserType;
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
          .type(UserType.NORMAL)
          .userData(UserData.builder()
            .userId("1234")
            .build())
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
