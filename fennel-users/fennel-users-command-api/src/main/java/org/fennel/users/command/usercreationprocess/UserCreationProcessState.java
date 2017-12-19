package org.fennel.users.command.usercreationprocess;

public enum UserCreationProcessState {
  PENDING_CHECK,
  CHECKED,
  CHECK_FAILED,
  CONFIRMED,
  TERMINATED
}
