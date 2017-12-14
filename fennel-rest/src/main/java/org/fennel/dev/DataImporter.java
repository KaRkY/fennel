package org.fennel.dev;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.fennel.common.StringUtil;
import org.fennel.users.api.user.UsernameAvaibleQuery;
import org.fennel.users.api.user.UsernameAvaibleQueryResponse;
import org.fennel.users.api.usercreationprocess.ConfirmCommand;
import org.fennel.users.api.usercreationprocess.CreateCommand;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Component
@Profile("dev")
@Slf4j
public class DataImporter implements CommandLineRunner {

  private final CommandGateway commandGateway;
  private final QueryGateway   queryGateway;
  private final Scheduler      scheduler;

  public DataImporter(
    final CommandGateway commandGateway,
    final QueryGateway queryGateway,
    final Scheduler scheduler) {
    this.commandGateway = commandGateway;
    this.queryGateway = queryGateway;
    this.scheduler = scheduler;
  }

  @Override
  public void run(final String... args) throws Exception {

    Mono.fromFuture(queryGateway
      .send(UsernameAvaibleQuery.builder().build(), UsernameAvaibleQueryResponse.class))
      .filter(UsernameAvaibleQueryResponse::isAvaible)
      .subscribeOn(scheduler)
      .subscribe(
        response -> {
          final String process1Id = UUID.randomUUID().toString();
          final String user1Pin = StringUtil.random(25);
          final String process2Id = UUID.randomUUID().toString();
          final String user2Pin = StringUtil.random(25);
          final String process3Id = UUID.randomUUID().toString();
          final String user3Pin = StringUtil.random(25);

          log.info("Creating process {}", process1Id);
          commandGateway.sendAndWait(CreateCommand.builder()
            .processId(process1Id)
            .pin(user1Pin)
            .displayName("Svetina, Rene")
            .username("rene.svetina@gmail.com")
            .password("1234")
            .build());

          log.info("Creating process {}", process2Id);
          commandGateway.sendAndWait(CreateCommand.builder()
            .processId(process2Id)
            .pin(user2Pin)
            .displayName("Novak, Janez")
            .username("janez.novak@gmail.com")
            .password("1234")
            .build());

          log.info("Creating process {}", process3Id);
          commandGateway.sendAndWait(CreateCommand.builder()
            .processId(process3Id)
            .pin(user3Pin)
            .displayName("Novakec, Janez")
            .username("janez.novak@gmail.com")
            .password("1234")
            .build());

          log.info("Confirming user process {}", process1Id);
          commandGateway.sendAndWait(ConfirmCommand.builder()
            .processId(process1Id)
            .pin(user1Pin)
            .build());

          log.info("Confirming user process {}", process2Id);
          commandGateway.sendAndWait(ConfirmCommand.builder()
            .processId(process2Id)
            .pin(user2Pin)
            .build());
        },
        e -> log.error(e.getMessage(), e),
        () -> log.info("Import complete"));
  }

}
