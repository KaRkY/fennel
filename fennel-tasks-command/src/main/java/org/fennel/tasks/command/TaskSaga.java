package org.fennel.tasks.command;

import java.time.Duration;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.ScheduleToken;
import org.fennel.api.tasks.events.TaskCompletedEvent;
import org.fennel.api.tasks.events.TaskCreatedEvent;
import org.fennel.api.tasks.events.TaskEscalationEvent;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskSaga {

  @Autowired
  private transient CommandGateway commandGateway;
  @Autowired
  private transient EventScheduler eventScheduler;

  private ScheduleToken escalationToken;

  @StartSaga
  @SagaEventHandler(associationProperty = "taskId")
  public void handle(final TaskCreatedEvent event) {
    escalationToken = eventScheduler.schedule(Duration.ofDays(5), new TaskEscalationEvent(event.getTaskId()));
  }

  @SagaEventHandler(associationProperty = "taskId")
  @EndSaga
  public void handle(final TaskCompletedEvent event) {
    eventScheduler.cancelSchedule(escalationToken);
  }
}