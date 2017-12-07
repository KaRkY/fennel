package org.fennel.users.query;

import org.axonframework.queryhandling.QueryHandler;
import org.fennel.users.api.UsersQueryObjectRepository;
import org.fennel.users.api.query.AnyUserExistsRequest;
import org.fennel.users.api.query.AnyUserExistsResponse;
import org.fennel.users.api.query.UsernameExistsQueryRequest;
import org.fennel.users.api.query.UsernameExistsQueryResponse;
import org.springframework.stereotype.Component;

@Component
public class UsersQueryObjectHandler {

  private final UsersQueryObjectRepository repository;

  public UsersQueryObjectHandler(final UsersQueryObjectRepository repository) {
    this.repository = repository;
  }

  @QueryHandler
  public UsernameExistsQueryResponse handle(final UsernameExistsQueryRequest request) {
    return UsernameExistsQueryResponse.builder()
      .username(request.getUsername())
      .exists(repository.containsUser(request.getUsername()))
      .build();
  }

  @QueryHandler
  public AnyUserExistsResponse handle(final AnyUserExistsRequest request) {
    return AnyUserExistsResponse.builder()
      .exists(repository.containsAnyUser())
      .build();
  }
}
