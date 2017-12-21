package org.fennel.users.query.usercreationprocess;

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
public class ListUserCreationProcessesQueryResponse {

  private final Page<UserCreationProcessQueryObject> page;

}
