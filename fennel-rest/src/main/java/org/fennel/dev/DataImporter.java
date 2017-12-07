package org.fennel.dev;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.fennel.users.api.Password;
import org.fennel.users.api.UserId;
import org.fennel.users.api.UserPin;
import org.fennel.users.api.Username;
import org.fennel.users.api.commands.ConfirmUserCommand;
import org.fennel.users.api.commands.CreateUserCommand;
import org.fennel.users.api.query.AnyUserExistsRequest;
import org.fennel.users.api.query.AnyUserExistsResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
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
      .send(new AnyUserExistsRequest(), AnyUserExistsResponse.class)
      .thenAccept(response -> {
        if (!response.isExists()) {
          final UserId user1Id = UserId.randomUUID();
          final UserPin user1Pin = UserPin.random(25);
          final UserId user2Id = UserId.randomUUID();
          final UserPin user2Pin = UserPin.random(25);

          commandGateway.sendAndWait(CreateUserCommand.builder()
            .userId(user1Id)
            .displayName("Svetina, Rene")
            .username(Username.of("rene.svetina@gmail.com"))
            .password(Password.of("1234"))
            .pin(user1Pin)
            .build());

          commandGateway.sendAndWait(CreateUserCommand.builder()
            .userId(user2Id)
            .displayName("Novak, Janez")
            .username(Username.of("janez.novak@gmail.com"))
            .password(Password.of("1234"))
            .pin(user2Pin)
            .build());

          commandGateway.sendAndWait(ConfirmUserCommand.builder()
            .userId(user1Id)
            .pin(user1Pin)
            .build());

          commandGateway.sendAndWait(ConfirmUserCommand.builder()
            .userId(user2Id)
            .pin(user2Pin)
            .build());
        }
      });
  }

}
