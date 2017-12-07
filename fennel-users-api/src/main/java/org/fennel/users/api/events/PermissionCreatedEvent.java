package org.fennel.users.api.events;

import org.fennel.users.api.PermissionName;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PermissionCreatedEvent {

  private final PermissionName permissionName;
  private final String         description;

}
