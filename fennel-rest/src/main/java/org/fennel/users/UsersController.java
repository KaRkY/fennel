package org.fennel.users;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.fennel.common.StringUtil;
import org.fennel.users.api.commands.ConfirmUserCommand;
import org.fennel.users.api.commands.CreateUserCommand;
import org.fennel.users.api.query.ConfirmUserRequest;
import org.fennel.users.api.query.ConfirmUserResponse;
import org.fennel.users.api.query.CreateUserRequest;
import org.fennel.users.api.query.CreateUserResponse;
import org.fennel.users.api.query.ListUsersRequest;
import org.fennel.users.api.query.ListUsersResponse;
import org.fennel.users.api.query.UsernameExistsQueryRequest;
import org.fennel.users.api.query.UsernameExistsQueryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

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
  public Mono<ResponseEntity<CreateUserResponse>> createUser(
    @RequestBody final CreateUserRequest createUserRequest) {

    return Mono.fromFuture(queryGateway
      .send(UsernameExistsQueryRequest.builder()
        .username(createUserRequest.getUsername())
        .build(),
        UsernameExistsQueryResponse.class))
      .flatMap(r -> {
        if (!r.isExists()) {
          final String userId = UUID.randomUUID().toString();
          final String userPin = StringUtil.random(25);

          return Mono.fromFuture(commandGateway
            .send(CreateUserCommand.builder()
              .userId(userId)
              .displayName(createUserRequest.getDisplayName())
              .username(createUserRequest.getUsername())
              .password(createUserRequest.getPassword())
              .pin(userPin)
              .build()))
            .map(r1 -> CreateUserResponse.builder()
              .userId(userId)
              .pin(userPin)
              .build())
            .map(ResponseEntity::ok);
        } else
          return Mono.just(ResponseEntity.badRequest().build());
      });
  }

  @PostMapping("/{userId}/confirm")
  public Mono<ResponseEntity<ConfirmUserResponse>> createUser(
    @PathVariable("userId") final String userId,
    @RequestBody final ConfirmUserRequest request) {

    return Mono.fromFuture(commandGateway.<Boolean>send(ConfirmUserCommand.builder()
      .userId(userId)
      .pin(request.getPin())
      .build()))
      .map(r -> r ? ResponseEntity.ok(ConfirmUserResponse.builder()
        .userId(userId)
        .build()) : ResponseEntity.badRequest().build());
  }

  @GetMapping
  public Mono<ResponseEntity<ListUsersResponse>> list() {

    return Mono
      .fromFuture(queryGateway.send(ListUsersRequest.builder().build(), ListUsersResponse.class))
      .map(ResponseEntity::ok)
      .onErrorReturn(ResponseEntity.badRequest().build());
  }
}
