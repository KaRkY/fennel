package org.fennel.controllers;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.fennel.api.users.UserId;
import org.fennel.api.users.commands.ConfirmUserCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/command")
public class CommandController {

  private final CommandGateway commandGateway;

  public CommandController(final CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @GetMapping
  public String test() {

    commandGateway.send(new ConfirmUserCommand(UserId.of("954d3fac-ca59-4f33-acd6-77aafcab6171"), "test"));

    return "OK";
  }
}
