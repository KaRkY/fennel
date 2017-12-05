package org.fennel.tasks.command;

import java.time.Duration;

import org.axonframework.test.saga.SagaTestFixture;
import org.fennel.api.tasks.TaskId;
import org.fennel.api.tasks.events.TaskCompletedEvent;
import org.fennel.api.tasks.events.TaskCreatedEvent;
import org.fennel.api.tasks.events.TaskEscalationEvent;
import org.junit.Before;
import org.junit.Test;

public class TaskSagaTests {
  private SagaTestFixture<TaskSaga> fixture;

  @Before
  public void setUp() {
    fixture = new SagaTestFixture<>(TaskSaga.class);
  }

  @Test
  public void createTask() throws Exception {
    fixture
      .givenNoPriorActivity()
      .whenPublishingA(new TaskCreatedEvent(TaskId.of("1234")))
      .expectScheduledEvent(Duration.ofDays(5), new TaskEscalationEvent(TaskId.of("1234")));
  }

  @Test
  public void completeTaskInTime() throws Exception {
    fixture
      .givenAPublished(new TaskCreatedEvent(TaskId.of("1234")))
      .andThenTimeElapses(Duration.ofDays(2))
      .whenPublishingA(new TaskCompletedEvent(TaskId.of("1234")))
      .expectNoScheduledEvents();
  }
}
