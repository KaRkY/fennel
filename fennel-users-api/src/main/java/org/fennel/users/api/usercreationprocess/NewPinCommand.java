package org.fennel.users.api.usercreationprocess;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NewPinCommand {

  @TargetAggregateIdentifier
  private final String userId;
  private final String pin;

}