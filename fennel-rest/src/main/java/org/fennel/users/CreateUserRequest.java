package org.fennel.users;

public class CreateUserRequest {
  private String displayName;
  private String username;
  private String password;

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(final String displayName) {
    this.displayName = displayName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder
      .append("CreateUserRequest [displayName=")
      .append(displayName)
      .append(", username=")
      .append(username)
      .append(", password=")
      .append(password)
      .append("]");
    return builder.toString();
  }

}
