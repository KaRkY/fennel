package org.fennel.tasks.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.tasks.api.TaskId;
import org.fennel.tasks.api.commands.CreateTaskCommand;
import org.fennel.tasks.api.events.TaskCreatedEvent;
import org.junit.Before;
import org.junit.Test;

public class TaskTests {
  private AggregateTestFixture<Task> fixture;

  @Before
  public void setUp() {
    fixture = new AggregateTestFixture<>(Task.class);
  }

  @Test
  public void createTask() throws Exception {
    fixture
        .given()
        .when(CreateTaskCommand.builder()
            .taskId(TaskId.of("1234"))
            .build())
        .expectEvents(TaskCreatedEvent.builder()
            .taskId(TaskId.of("1234"))
            .build());
  }
}
