package org.fennel.users.query.usercreationprocess;

import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.fennel.users.api.usercreationprocess.ListUserCreationProcessesQuery;
import org.fennel.users.api.usercreationprocess.ListUserCreationProcessesQueryResponse;
import org.fennel.users.api.usercreationprocess.UserCreationProcessQueryObject;

public class Handler {

  private final Repository repository;

  public Handler(final Repository repository) {
    this.repository = repository;
  }

  @QueryHandler
  public ListUserCreationProcessesQueryResponse handle(final ListUserCreationProcessesQuery request) {
    final Integer count = repository.count();
    final List<UserCreationProcessQueryObject> users = repository.list(request.getPagination());

    return ListUserCreationProcessesQueryResponse.builder()
      .pagination(request.getPagination())
      .size(count)
      .users(users)
      .build();
  }
}
