package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.users.api.commands.CreateUserCreationProcessCommand;
import org.fennel.users.api.events.UserCreationProcessCreated;
import org.fennel.users.commands.UserCreationProcess;
import org.junit.Before;
import org.junit.Test;

public class UserCreationProcessTests {
  private AggregateTestFixture<UserCreationProcess> fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture<>(UserCreationProcess.class);
  }

  @Test
  public void createUser() throws Exception {
    fixture
        .given()
        .when(CreateUserCreationProcessCommand.builder()
            .processId("12344")
            .pin("123")
            .build())
        .expectEvents(UserCreationProcessCreated.builder()
            .processId("12344")
            .pin("123")
            .build());
  }
}
