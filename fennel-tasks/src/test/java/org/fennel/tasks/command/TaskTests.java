package org.fennel.tasks.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.fennel.api.tasks.TaskId;
import org.fennel.api.tasks.commands.CreateTaskCommand;
import org.fennel.api.tasks.events.TaskCreatedEvent;
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
      .when(new CreateTaskCommand(TaskId.of("1234")))
      .expectEvents(new TaskCreatedEvent(TaskId.of("1234")));
  }
}
