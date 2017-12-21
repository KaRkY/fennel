package org.fennel.users.services.impl;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.fennel.common.services.IdGeneratorService;
import org.fennel.common.util.AuthorizationData;
import org.fennel.users.command.usercreationprocess.CreateCommand;
import org.fennel.users.services.AdminUserService;
import org.fennel.users.services.User;

public class DefaultAdminUserService implements AdminUserService {

  private final CommandGateway     commandGateway;
  private final IdGeneratorService idGenerator;

  public DefaultAdminUserService(
    final CommandGateway commandGateway,
    final IdGeneratorService idGenerator) {
    this.commandGateway = commandGateway;
    this.idGenerator = idGenerator;
  }

  @Override
  public String create(final User user, final AuthorizationData authorizationData) {
    final String processId = idGenerator.generate("userCreationProcess");

    final CreateCommand createCommand = CreateCommand.builder()
      .processId(processId)
      .displayName(user.getDisplayName())
      .username(user.getUsername())
      .password(user.getPassword())
      .confirmed(false)
      .authorizationData(authorizationData)
      .build();

    commandGateway.sendAndWait(createCommand);

    return processId;
  }

}
