package org.fennel.users.services.impl;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.fennel.common.services.IdGeneratorService;
import org.fennel.common.util.AuthorizationData;
import org.fennel.users.command.usercreationprocess.CreateCommand;
import org.fennel.users.services.AdminUserService;
import org.fennel.users.services.User;

public class DefaultAdminUserService implements AdminUserService {

  private CommandGateway     commandGateway;
  private IdGeneratorService idGenerator;

  public DefaultAdminUserService(
    CommandGateway commandGateway,
    IdGeneratorService idGenerator) {
    this.commandGateway = commandGateway;
    this.idGenerator = idGenerator;
  }

  @Override
  public String create(User user, AuthorizationData authorizationData) {
    String processId = idGenerator.generate("userCreationProcess");

    CreateCommand createCommand = CreateCommand.builder()
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
