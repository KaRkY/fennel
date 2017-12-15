package org.fennel.users;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.fennel.users.api.user.GetUserQuery;
import org.fennel.users.api.user.GetUserQueryResponse;
import org.fennel.users.api.user.ListUsersQuery;
import org.fennel.users.api.user.ListUsersQueryResponse;
import org.fennel.users.api.user.UserQueryObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@ExposesResourceFor(UserResource.class)
public class UsersController {

  private final CommandGateway                           commandGateway;
  private final QueryGateway                             queryGateway;
  private final PagedResourcesAssembler<UserQueryObject> pagedResourcesAssembler;
  private final UserResourceAssembler                    userResourceAssembler;

  public UsersController(
    final CommandGateway commandGateway,
    final QueryGateway queryGateway,
    final PagedResourcesAssembler<UserQueryObject> pagedResourcesAssembler,
    final UserResourceAssembler userResourceAssembler) {
    this.commandGateway = commandGateway;
    this.queryGateway = queryGateway;
    this.pagedResourcesAssembler = pagedResourcesAssembler;
    this.userResourceAssembler = userResourceAssembler;
  }

  @GetMapping(produces = "application/json")
  public Mono<ResponseEntity<PagedResources<UserResource>>> list(final Pageable pageable) {
    return Mono
      .fromFuture(queryGateway
        .send(ListUsersQuery.builder()
          .pageable(pageable)
          .build(),
          ListUsersQueryResponse.class))
      .map(resList -> pagedResourcesAssembler.toResource(resList.getPage(), userResourceAssembler))
      .map(ResponseEntity::ok);
  }

  @GetMapping(value = "/{userId}", produces = "application/json")
  public Mono<ResponseEntity<UserResource>> get(@PathVariable("userId") final String userId) {
    return Mono
      .fromFuture(queryGateway
        .send(GetUserQuery.builder()
          .userId(userId)
          .build(),
          GetUserQueryResponse.class))
      .map(GetUserQueryResponse::getResult)
      .flatMap(Mono::justOrEmpty)
      .map(userResourceAssembler::toResource)
      .map(ResponseEntity::ok)
      .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }
}
