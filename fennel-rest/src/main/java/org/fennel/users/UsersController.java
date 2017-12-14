package org.fennel.users;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.fennel.users.api.user.ListUsersQuery;
import org.fennel.users.api.user.ListUsersQueryResponse;
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
  public Mono<ResponseEntity<ListUsersQueryResponse>> list() {

    return Mono
      .fromFuture(queryGateway.send(ListUsersQuery.builder().build(), ListUsersQueryResponse.class))
      .map(ResponseEntity::ok)
      .onErrorReturn(ResponseEntity.badRequest().build());
  }
}
