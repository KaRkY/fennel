package org.fennel.users.query.user;

import org.axonframework.queryhandling.QueryHandler;
import org.fennel.users.api.user.AnyUserAvaibleQuery;
import org.fennel.users.api.user.AnyUserAvaibleQueryResponse;
import org.fennel.users.api.user.GetUserQuery;
import org.fennel.users.api.user.GetUserQueryResponse;
import org.fennel.users.api.user.ListUsersQuery;
import org.fennel.users.api.user.ListUsersQueryResponse;
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
  public AnyUserAvaibleQueryResponse handle(final AnyUserAvaibleQuery request) {
    return AnyUserAvaibleQueryResponse.builder()
      .avaible(!repository.containsAnyUser())
      .build();
  }

  @QueryHandler
  public ListUsersQueryResponse handle(final ListUsersQuery request) {
    return ListUsersQueryResponse.builder()
      .page(repository.list(request.getPageable()))
      .build();
  }

  @QueryHandler
  public GetUserQueryResponse handle(final GetUserQuery request) {
    return GetUserQueryResponse.builder()
      .result(repository.get(request.getUserId()))
      .build();
  }
}
