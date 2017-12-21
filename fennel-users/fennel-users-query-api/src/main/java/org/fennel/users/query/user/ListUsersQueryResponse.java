package org.fennel.users.query.user;

import org.fennel.common.util.Page;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Builder
@Wither
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ListUsersQueryResponse {

  private final Page<UserQueryObject> page;

}
