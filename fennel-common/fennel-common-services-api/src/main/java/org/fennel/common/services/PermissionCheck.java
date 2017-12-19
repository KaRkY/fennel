package org.fennel.common.services;

public interface PermissionCheck {

  boolean check(String module, String aggregate, String permission);

}
