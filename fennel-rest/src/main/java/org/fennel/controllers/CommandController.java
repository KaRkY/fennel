package org.fennel.controllers;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.fennel.api.users.Password;
import org.fennel.api.users.UserId;
import org.fennel.api.users.UserPin;
import org.fennel.api.users.Username;
import org.fennel.api.users.commands.CreateUserCommand;
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

    commandGateway.sendAndWait(new CreateUserCommand(
      UserId.randomUUID(),
      "user 1",
      Username.of("user@gmail.com"),
      Password.of("1234"),
      UserPin.random(8)));

    return "OK";
  }
}
