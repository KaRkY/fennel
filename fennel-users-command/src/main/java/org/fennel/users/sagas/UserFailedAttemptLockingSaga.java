package org.fennel.users.sagas;

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
import org.fennel.api.users.commands.LockUserCommand;
import org.fennel.api.users.events.UserAuthorizationFailedEvent;
import org.fennel.api.users.events.UserAuthorizedEvent;
import org.fennel.api.users.events.UserUnlockedEvent;
import org.springframework.beans.factory.annotation.Autowired;

public class UserFailedAttemptLockingSaga {

  @Autowired
  private transient CommandGateway commandGateway;
  @Autowired
  private transient EventScheduler eventScheduler;

  private final LinkedList<Instant> attempts = new LinkedList<>();
  private ScheduleToken             lockedToken;

  @StartSaga
  @SagaEventHandler(associationProperty = "userId")
  public void handle(final UserAuthorizationFailedEvent event, @Timestamp final Instant time) {
    attempts.add(time);
    if (attempts.size() > 5) {
      attempts.removeFirst();
    } else {
      if (attempts.size() == 5) {
        if (Duration.between(attempts.peekFirst(), time).toMinutes() < 5) {
          commandGateway.send(LockUserCommand.builder()
            .userId(event.getUserId())
            .build());
          lockedToken = eventScheduler.schedule(Duration.ofMinutes(5), UserUnlockedEvent.builder()
            .userId(event.getUserId())
            .build());
        }
      }
    }
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "userId")
  public void handle(final UserUnlockedEvent event) {
    if (lockedToken != null) {
      eventScheduler.cancelSchedule(lockedToken);
    }
    attempts.clear();
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "userId")
  public void handle(final UserAuthorizedEvent event) {
    if (lockedToken != null) {
      eventScheduler.cancelSchedule(lockedToken);
    }
    attempts.clear();
  }
}
