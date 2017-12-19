package org.fennel.users.query.usercreationprocess;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUserCreationProcessQueryResponse {

  private final Optional<UserCreationProcessQueryObject> result;

}
