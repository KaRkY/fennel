package org.fennel.users.query;

import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.fennel.users.api.query.AnyUserExistsRequest;
import org.fennel.users.api.query.AnyUserExistsResponse;
import org.fennel.users.api.query.ListUsersRequest;
import org.fennel.users.api.query.ListUsersResponse;
import org.fennel.users.api.query.UserQueryObject;
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

  @QueryHandler
  public ListUsersResponse handle(final ListUsersRequest request) {
    final Integer count = repository.count();
    final List<UserQueryObject> users = repository.list(request.getPagination());

    return ListUsersResponse.builder()
      .pagination(request.getPagination())
      .size(count)
      .users(users)
      .build();
  }
}
