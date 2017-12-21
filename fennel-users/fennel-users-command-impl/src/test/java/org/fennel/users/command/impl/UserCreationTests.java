package org.fennel.users.command.impl;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.common.services.UsersProperties;
import org.fennel.common.util.UserData;
import org.fennel.users.command.user.CreateCommand;
import org.fennel.users.command.user.CreatedEvent;
import org.fennel.users.command.user.UserType;
import org.junit.Before;
import org.junit.Test;

public class UserCreationTests {
  private AggregateTestFixture<User> fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture<>(User.class);
    fixture.registerInjectableResource(UsersProperties.builder()
      .adminGroupName("admin")
      .internalGroupName("internal")
      .publicGroupName("public")
      .systemUserId("system")
      .anonymousUserId("anonymous")
      .build());
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
        .type(UserType.NORMAL)
        .userData(UserData.builder()
          .userId("1234")
          .build())
        .build())
      .expectEvents(CreatedEvent.builder()
        .userId("1234")
        .displayName("User 1")
        .username("user1@gmail.com")
        .password("1234")
        .locked(false)
        .type(UserType.NORMAL)
        .userData(UserData.builder()
          .userId("1234")
          .build())
        .build());
  }

}
