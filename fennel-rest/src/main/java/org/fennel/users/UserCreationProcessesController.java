package org.fennel.users;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.fennel.users.api.usercreationprocess.ListUserCreationProcessesQuery;
import org.fennel.users.api.usercreationprocess.ListUserCreationProcessesQueryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user_creation_processes")
public class UserCreationProcessesController {

  private final CommandGateway commandGateway;
  private final QueryGateway   queryGateway;

  public UserCreationProcessesController(
    final CommandGateway commandGateway,
    final QueryGateway queryGateway) {
    this.commandGateway = commandGateway;
    this.queryGateway = queryGateway;
  }

  @GetMapping
  public Mono<ResponseEntity<ListUserCreationProcessesQueryResponse>> list() {

    return Mono
      .fromFuture(queryGateway.send(ListUserCreationProcessesQuery.builder().build(),
        ListUserCreationProcessesQueryResponse.class))
      .map(ResponseEntity::ok)
      .onErrorReturn(ResponseEntity.badRequest().build());
  }
}
