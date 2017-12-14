package org.fennel.users.query.usercreationprocess;

import org.axonframework.eventhandling.EventHandler;
import org.fennel.users.api.usercreationprocess.ConfirmedEvent;
import org.fennel.users.api.usercreationprocess.CreatedEvent;
import org.fennel.users.api.usercreationprocess.DataCheckFailedEvent;
import org.fennel.users.api.usercreationprocess.DataCheckedEvent;
import org.fennel.users.api.usercreationprocess.TerminatedEvent;
import org.fennel.users.api.usercreationprocess.UserCreationProcessQueryObject;

public class Processor {

  private final Repository repository;

  public Processor(final Repository repository) {
    this.repository = repository;
  }

  @EventHandler
  public void on(final CreatedEvent event) {
    repository.insert(UserCreationProcessQueryObject.builder()
      .processId(event.getProcessId())
      .displayName(event.getDisplayName())
      .username(event.getUsername())
      .password(event.getPassword())
      .state("pending check")
      .build());
  }

  @EventHandler
  public void on(final DataCheckedEvent event) {
    repository.updateState(event.getProcessId(), "checked");
  }

  @EventHandler
  public void on(final TerminatedEvent event) {
    repository.updateState(event.getProcessId(), "terminated");
  }

  @EventHandler
  public void on(final ConfirmedEvent event) {
    repository.updateState(event.getProcessId(), "confirmed");
  }

  @EventHandler
  public void on(final DataCheckFailedEvent event) {
    repository.updateState(event.getProcessId(), "check failed");
  }
}
