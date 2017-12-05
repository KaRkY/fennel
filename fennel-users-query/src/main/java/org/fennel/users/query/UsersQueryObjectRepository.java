package org.fennel.users.query;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class UsersQueryObjectRepository {

  private final DSLContext create;

  public UsersQueryObjectRepository(final DSLContext create) {
    this.create = create;
  }

}
