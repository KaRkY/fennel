package org.fennel.api.users;

import java.io.Serializable;
import java.util.Objects;

public class GroupName implements Serializable {
  private static final long serialVersionUID = 4589305410669002230L;
  private final String      groupName;

  private GroupName(final String groupName) {
    super();
    this.groupName = groupName;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final GroupName other = (GroupName) o;

    return Objects.equals(groupName, other.groupName);

  }

  @Override
  public int hashCode() {
    return groupName.hashCode();
  }

  @Override
  public String toString() {
    return groupName;
  }

  public static GroupName of(final String groupName) {
    return new GroupName(Objects.requireNonNull(groupName, "groupName"));
  }
}
