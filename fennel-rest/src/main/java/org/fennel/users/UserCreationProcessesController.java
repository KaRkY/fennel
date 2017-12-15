package org.fennel.users;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.fennel.common.StringUtil;
import org.fennel.users.api.usercreationprocess.CreateCommand;
import org.fennel.users.api.usercreationprocess.GetUserCreationProcessQuery;
import org.fennel.users.api.usercreationprocess.GetUserCreationProcessQueryResponse;
import org.fennel.users.api.usercreationprocess.ListUserCreationProcessesQuery;
import org.fennel.users.api.usercreationprocess.ListUserCreationProcessesQueryResponse;
import org.fennel.users.api.usercreationprocess.UserCreationProcessQueryObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user_creation_processes")
@ExposesResourceFor(UserCreationProcessResource.class)
public class UserCreationProcessesController {

  private final CommandGateway                                          commandGateway;
  private final QueryGateway                                            queryGateway;
  private final PagedResourcesAssembler<UserCreationProcessQueryObject> pagedResourcesAssembler;
  private final UserCreationProcessResourceAssembler                    userCreationProcessResourceAssembler;
  private final EntityLinks                                             entityLinks;

  public UserCreationProcessesController(
    final CommandGateway commandGateway,
    final QueryGateway queryGateway,
    final PagedResourcesAssembler<UserCreationProcessQueryObject> pagedResourcesAssembler,
    final UserCreationProcessResourceAssembler userCreationProcessResourceAssembler,
    final EntityLinks entityLinks) {
    this.commandGateway = commandGateway;
    this.queryGateway = queryGateway;
    this.pagedResourcesAssembler = pagedResourcesAssembler;
    this.userCreationProcessResourceAssembler = userCreationProcessResourceAssembler;
    this.entityLinks = entityLinks;
  }

  @PostMapping(path = "create_process", consumes = "application/json")
  public Mono<ResponseEntity<Void>> create(@RequestBody final CreateUser createUser) {
    final CreateCommand command = CreateCommand.builder()
      .processId(UUID.randomUUID().toString())
      .pin(StringUtil.random(25))
      .displayName(createUser.getDisplayName())
      .username(createUser.getUsername())
      .password(createUser.getPassword())
      .build();

    return Mono
      .fromFuture(commandGateway.send(command))
      .map(response -> ResponseEntity.ok().<Void>build())
      .onErrorReturn(ResponseEntity.badRequest().build());
  }

  @GetMapping(produces = "application/json")
  public Mono<ResponseEntity<PagedResources<UserCreationProcessResource>>> list(final Pageable pageable) {

    return Mono
      .fromFuture(queryGateway
        .send(ListUserCreationProcessesQuery.builder()
          .pageable(pageable)
          .build(),
          ListUserCreationProcessesQueryResponse.class))
      .map(resList -> pagedResourcesAssembler.toResource(resList.getPage(), userCreationProcessResourceAssembler))
      .map(resource -> {
        resource.add(entityLinks.linkFor(UserCreationProcessResource.class).slash("create_process").withRel("create"));
        return resource;
      })
      .map(ResponseEntity::ok)
      .onErrorReturn(ResponseEntity.badRequest().build());
  }

  @GetMapping(value = "/{processId}", produces = "application/json")
  public Mono<ResponseEntity<UserCreationProcessResource>> get(@PathVariable("processId") final String processId) {
    return Mono
      .fromFuture(queryGateway
        .send(GetUserCreationProcessQuery.builder()
          .processId(processId)
          .build(),
          GetUserCreationProcessQueryResponse.class))
      .map(GetUserCreationProcessQueryResponse::getResult)
      .flatMap(Mono::justOrEmpty)
      .map(userCreationProcessResourceAssembler::toResource)
      .map(ResponseEntity::ok)
      .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }
}
