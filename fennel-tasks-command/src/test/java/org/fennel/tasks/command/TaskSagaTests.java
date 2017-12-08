package org.fennel.tasks.command;

import java.time.Duration;

import org.axonframework.test.saga.SagaTestFixture;
import org.fennel.tasks.api.events.TaskCompletedEvent;
import org.fennel.tasks.api.events.TaskCreatedEvent;
import org.fennel.tasks.api.events.TaskEscalationEvent;
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
      .whenPublishingA(TaskCreatedEvent.builder()
        .taskId("1234")
        .build())
      .expectScheduledEvent(Duration.ofDays(5), TaskEscalationEvent.builder()
        .taskId("1234")
        .build());
  }

  @Test
  public void completeTaskInTime() throws Exception {
    fixture
      .givenAPublished(TaskCreatedEvent.builder()
        .taskId("1234")
        .build())
      .andThenTimeElapses(Duration.ofDays(2))
      .whenPublishingA(TaskCompletedEvent.builder()
        .taskId("1234")
        .build())
      .expectNoScheduledEvents();
  }
}
