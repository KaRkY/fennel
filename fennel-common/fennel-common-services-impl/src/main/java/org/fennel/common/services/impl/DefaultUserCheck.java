package org.fennel.common.services.impl;

import java.util.concurrent.CompletableFuture;

import org.fennel.common.services.User;
import org.fennel.common.services.UserCheck;
import org.fennel.services.jooq.Tables;
import org.jooq.DSLContext;
import org.springframework.transaction.annotation.Transactional;

public class DefaultUserCheck implements UserCheck {

  private final DSLContext create;

  public DefaultUserCheck(final DSLContext create) {
    this.create = create;
  }

  @Override
  @Transactional
  public CompletableFuture<Boolean> check(final User user) {
    final boolean exists = create.fetchExists(create
      .selectOne()
      .from(Tables.RESERVED_USERNAMES)
      .where(Tables.RESERVED_USERNAMES.USERNAME.eq(user.getUsername())));

    if (exists) return CompletableFuture.completedFuture(false);
    else {
      create
        .insertInto(Tables.RESERVED_USERNAMES)
        .columns(Tables.RESERVED_USERNAMES.PROCESS_ID, Tables.RESERVED_USERNAMES.USERNAME)
        .values(user.getProcessId(), user.getUsername())
        .execute();
      return CompletableFuture.completedFuture(true);
    }
  }

}
