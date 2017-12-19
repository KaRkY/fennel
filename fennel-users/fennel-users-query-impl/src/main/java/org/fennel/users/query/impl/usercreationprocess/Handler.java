package org.fennel.users.query.impl.usercreationprocess;

import org.axonframework.queryhandling.QueryHandler;
import org.fennel.users.query.usercreationprocess.GetUserCreationProcessQuery;
import org.fennel.users.query.usercreationprocess.GetUserCreationProcessQueryResponse;
import org.fennel.users.query.usercreationprocess.ListUserCreationProcessesQuery;
import org.fennel.users.query.usercreationprocess.ListUserCreationProcessesQueryResponse;

public class Handler {

  private final Repository repository;

  public Handler(final Repository repository) {
    this.repository = repository;
  }

  @QueryHandler
  public ListUserCreationProcessesQueryResponse handle(final ListUserCreationProcessesQuery request) {
    return ListUserCreationProcessesQueryResponse.builder()
      .page(repository.list(request.getPageable()))
      .build();
  }

  @QueryHandler
  public GetUserCreationProcessQueryResponse handle(final GetUserCreationProcessQuery request) {
    return GetUserCreationProcessQueryResponse.builder()
      .result(repository.get(request.getProcessId()))
      .build();
  }
}
