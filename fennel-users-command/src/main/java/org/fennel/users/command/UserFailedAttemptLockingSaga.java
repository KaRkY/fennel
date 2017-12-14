package org.fennel.users.command;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.ScheduleToken;
import org.axonframework.spring.stereotype.Saga;
import org.fennel.users.api.user.LockCommand;
import org.fennel.users.api.user.AuthorizationFailedEvent;
import org.fennel.users.api.user.AuthorizedEvent;
import org.fennel.users.api.user.UnlockedEvent;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class UserFailedAttemptLockingSaga {

  @Autowired
  private transient CommandGateway commandGateway;
  @Autowired
  private transient EventScheduler eventScheduler;

  private final LinkedList<Instant> attempts = new LinkedList<>();
  private ScheduleToken             lockedToken;

  @StartSaga
  @SagaEventHandler(associationProperty = "userId")
  public void handle(final AuthorizationFailedEvent event, @Timestamp final Instant time) {
    attempts.add(time);
    if (attempts.size() > 5) {
      attempts.removeFirst();
    } else {
      if (attempts.size() == 5) {
        if (Duration.between(attempts.peekFirst(), time).toMinutes() < 5) {
          commandGateway.send(LockCommand.builder()
            .userId(event.getUserId())
            .build());
          lockedToken = eventScheduler.schedule(Duration.ofMinutes(5), UnlockedEvent.builder()
            .userId(event.getUserId())
            .build());
        }
      }
    }
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "userId")
  public void handle(final UnlockedEvent event) {
    if (lockedToken != null) {
      eventScheduler.cancelSchedule(lockedToken);
    }
    attempts.clear();
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "userId")
  public void handle(final AuthorizedEvent event) {
    if (lockedToken != null) {
      eventScheduler.cancelSchedule(lockedToken);
    }
    attempts.clear();
  }
}
