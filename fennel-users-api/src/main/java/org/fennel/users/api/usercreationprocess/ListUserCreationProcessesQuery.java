package org.fennel.users.api.usercreationprocess;

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
public class ListUserCreationProcessesQuery {

  private final Pagination pagination;

}
