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
    return commandGateway.send(CreateUserCommand.builder()
        .userId(userId)
        .displayName(createUserRequest.getDisplayName())
        .username(Username.of(createUserRequest.getUsername()))
        .password(Password.of(createUserRequest.getPassword()))
        .pin(UserPin.random(8))
        .build())
        .thenApply(r -> CreateUserResponse.builder()
            .userId(userId.toString())
            .build());
  }
}
