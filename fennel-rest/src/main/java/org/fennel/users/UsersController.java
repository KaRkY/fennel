package org.fennel.users;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.fennel.users.api.query.ListUsersRequest;
import org.fennel.users.api.query.ListUsersResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping
  public Mono<ResponseEntity<ListUsersResponse>> list() {

    return Mono
      .fromFuture(queryGateway.send(ListUsersRequest.builder().build(), ListUsersResponse.class))
      .map(ResponseEntity::ok)
      .onErrorReturn(ResponseEntity.badRequest().build());
  }
}
