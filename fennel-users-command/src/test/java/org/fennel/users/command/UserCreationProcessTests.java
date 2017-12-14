package org.fennel.users.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.users.api.usercreationprocess.CreateCommand;
import org.fennel.users.api.usercreationprocess.CreatedEvent;
import org.fennel.users.command.UserCreationProcess;
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
        .when(CreateCommand.builder()
            .processId("12344")
            .pin("123")
            .build())
        .expectEvents(CreatedEvent.builder()
            .processId("12344")
            .pin("123")
            .build());
  }
}
