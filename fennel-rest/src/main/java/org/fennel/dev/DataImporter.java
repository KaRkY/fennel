package org.fennel.dev;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.fennel.common.StringUtil;
import org.fennel.users.api.commands.ConfirmUserCommand;
import org.fennel.users.api.commands.CreateUserCreationProcessCommand;
import org.fennel.users.api.query.AnyUserExistsRequest;
import org.fennel.users.api.query.AnyUserExistsResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Profile("dev")
@Slf4j
public class DataImporter implements CommandLineRunner {

  private final CommandGateway commandGateway;
  private final QueryGateway   queryGateway;

  public DataImporter(
    final CommandGateway commandGateway,
    final QueryGateway queryGateway) {
    this.commandGateway = commandGateway;
    this.queryGateway = queryGateway;
  }

  @Override
  public void run(final String... args) throws Exception {

    queryGateway
      .send(AnyUserExistsRequest.builder().build(), AnyUserExistsResponse.class)
      .thenAccept(response -> {
        if (!response.isExists()) {
          final String process1Id = UUID.randomUUID().toString();
          final String user1Pin = StringUtil.random(25);
          final String process2Id = UUID.randomUUID().toString();
          final String user2Pin = StringUtil.random(25);

          log.info("Creating process {}", process1Id);
          commandGateway.sendAndWait(CreateUserCreationProcessCommand.builder()
            .processId(process1Id)
            .pin(user1Pin)
            .displayName("Svetina, Rene")
            .username("rene.svetina@gmail.com")
            .password("1234")
            .build());

          log.info("Creating process {}", process2Id);
          commandGateway.sendAndWait(CreateUserCreationProcessCommand.builder()
            .processId(process2Id)
            .pin(user2Pin)
            .displayName("Novak, Janez")
            .username("janez.novak@gmail.com")
            .password("1234")
            .build());

          log.info("Confirming user process {}", process1Id);
          commandGateway.sendAndWait(ConfirmUserCommand.builder()
            .processId(process1Id)
            .pin(user1Pin)
            .build());

          log.info("Confirming user process {}", process2Id);
          commandGateway.sendAndWait(ConfirmUserCommand.builder()
            .processId(process2Id)
            .pin(user2Pin)
            .build());
        }
      })
      .whenComplete((r, e) -> {
        if (e != null) {
          log.error(e.getMessage(), e);
        }
      });
  }

}
