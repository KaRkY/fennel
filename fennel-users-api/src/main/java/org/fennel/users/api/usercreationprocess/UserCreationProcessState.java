package org.fennel.users.api.usercreationprocess;

public enum UserCreationProcessState {
  PENDING_CHECK,
  CHECKED,
  CHECK_FAILED,
  CONFIRMED,
  TERMINATED
}
