package org.fennel.services;

import java.util.concurrent.CompletableFuture;

public interface UserCheck {

  CompletableFuture<Boolean> check(User user);
}
