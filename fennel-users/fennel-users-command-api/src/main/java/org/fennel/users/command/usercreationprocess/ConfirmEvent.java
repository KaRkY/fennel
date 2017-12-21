package org.fennel.users.command.usercreationprocess;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Wither;

/**
 *
 * Event indicating that someone tried to Confirm this user.
 *
 * @author Rene Svetina(rene.svetina@gmail.com)
 *
 */

@Value
@Builder
@Wither
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfirmEvent {

  private final String processId;
  private final String pin;

}
