package org.fennel.users.query.user;

import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.fennel.users.api.user.ListUsersQuery;
import org.fennel.users.api.user.ListUsersQueryResponse;
import org.fennel.users.api.user.UserQueryObject;
import org.fennel.users.api.user.UsernameAvaibleQuery;
import org.fennel.users.api.user.UsernameAvaibleQueryResponse;

public class Handler {

  private final Repository repository;

  public Handler(final Repository repository) {
    this.repository = repository;
  }

  @QueryHandler
  public UsernameAvaibleQueryResponse handle(final UsernameAvaibleQuery request) {
    return UsernameAvaibleQueryResponse.builder()
      .avaible(!repository.containsUser(request.getUsername()))
      .build();
  }

  @QueryHandler
  public ListUsersQueryResponse handle(final ListUsersQuery request) {
    final Integer count = repository.count();
    final List<UserQueryObject> users = repository.list(request.getPagination());

    return ListUsersQueryResponse.builder()
      .pagination(request.getPagination())
      .size(count)
      .users(users)
      .build();
  }
}
