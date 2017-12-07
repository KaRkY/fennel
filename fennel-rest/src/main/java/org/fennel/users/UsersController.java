package org.fennel.users;

import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.fennel.users.api.Password;
import org.fennel.users.api.UserId;
import org.fennel.users.api.UserPin;
import org.fennel.users.api.Username;
import org.fennel.users.api.commands.ConfirmUserCommand;
import org.fennel.users.api.commands.CreateUserCommand;
import org.fennel.users.api.query.UsernameExistsQueryRequest;
import org.fennel.users.api.query.UsernameExistsQueryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

  private final CommandGateway commandGateway;
  private final QueryGateway   queryGateway;

  public UsersController(
    final CommandGateway commandGateway,
    final QueryGateway queryGateway) {
    this.commandGateway = commandGateway;
    this.queryGateway = queryGateway;
  }

  @PostMapping
  public CompletableFuture<ResponseEntity<CreateUserResponse>> createUser(
    @RequestBody final CreateUserRequest createUserRequest) {
    return queryGateway.send(
      UsernameExistsQueryRequest.builder()
        .username(Username.of(createUserRequest.getUsername()))
        .build(),
      UsernameExistsQueryResponse.class)
      .thenCompose(r -> {
        if (!r.isExists()) {
          final UserId userId = UserId.randomUUID();
          final UserPin userPin = UserPin.random(22);

          return commandGateway
            .send(CreateUserCommand.builder()
              .userId(userId)
              .displayName(createUserRequest.getDisplayName())
              .username(Username.of(createUserRequest.getUsername()))
              .password(Password.of(createUserRequest.getPassword()))
              .pin(userPin)
              .build())
            .thenApply(r1 -> CreateUserResponse.builder()
              .userId(userId.getValue())
              .pin(userPin.getValue())
              .build())
            .thenApply(response -> ResponseEntity.ok(response));
        } else
          return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
      });
  }

  @PostMapping("/{userId}/confirm")
  public CompletableFuture<ResponseEntity<ConfirmUserResponse>> createUser(
    @PathVariable("userId") final String userId,
    @RequestBody final ConfirmUserRequest request) {
    return commandGateway.<Boolean>send(ConfirmUserCommand.builder()
      .userId(UserId.of(userId))
      .pin(UserPin.of(request.getPin()))
      .build())
      .thenApply(r -> r ? ResponseEntity.ok(ConfirmUserResponse.builder()
        .userId(userId)
        .build()) : ResponseEntity.badRequest().build());

  }
}
