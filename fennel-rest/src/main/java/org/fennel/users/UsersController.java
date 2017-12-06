package org.fennel.users;

import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.fennel.api.users.Password;
import org.fennel.api.users.UserId;
import org.fennel.api.users.UserPin;
import org.fennel.api.users.Username;
import org.fennel.api.users.commands.CreateUserCommand;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

  private final CommandGateway commandGateway;

  public UsersController(final CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @PostMapping
  public CompletableFuture<CreateUserResponse> createUser(@RequestBody final CreateUserRequest createUserRequest) {
    final UserId userId = UserId.randomUUID();
    return commandGateway.send(new CreateUserCommand(
      UserId.randomUUID(),
      createUserRequest.getDisplayName(),
      Username.of(createUserRequest.getUsername()),
      Password.of(createUserRequest.getPassword()),
      UserPin.random(8)))
      .thenApply(r -> {
        final CreateUserResponse response = new CreateUserResponse();
        response.setUserId(userId.toString());
        return response;
      });
  }
}
