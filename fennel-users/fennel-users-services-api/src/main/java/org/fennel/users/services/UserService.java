package org.fennel.users.services;

import org.fennel.common.util.AuthorizationData;

public interface UserService {

  String create(User user, AuthorizationData authorizationData);
}
