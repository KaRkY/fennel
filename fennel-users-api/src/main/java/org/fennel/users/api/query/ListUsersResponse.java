package org.fennel.users.api.query;

import java.util.List;

import org.fennel.common.Pagination;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ListUsersResponse {

  private final Pagination            pagination;
  private final Integer               size;
  private final List<UserQueryObject> users;

}
