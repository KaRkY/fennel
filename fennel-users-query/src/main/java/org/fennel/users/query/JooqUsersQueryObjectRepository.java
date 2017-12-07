package org.fennel.users.query;

import org.fennel.users.api.Username;
import org.fennel.users.api.UsersQueryObjectRepository;
import org.fennel.users.api.events.UserConfirmedEvent;
import org.fennel.users.api.events.UserCreatedEvent;
import org.fennel.users.query.jooq.Tables;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class JooqUsersQueryObjectRepository implements UsersQueryObjectRepository {

  private final DSLContext create;

  public JooqUsersQueryObjectRepository(final DSLContext create) {
    this.create = create;
  }

  @Override
  public void insert(final UserCreatedEvent event) {
    create
      .insertInto(Tables.USERS)
      .columns(
        Tables.USERS.USER_ID,
        Tables.USERS.DISPLAY_NAME,
        Tables.USERS.USERNAME,
        Tables.USERS.PASSWORD,
        Tables.USERS.CONFIRMED,
        Tables.USERS.LOCKED)
      .values(
        event.getUserId().getValue(),
        event.getDisplayName(),
        event.getUsername().getValue(),
        event.getPassword().getValue(),
        event.isConfirmed(),
        event.isLocked())
      .execute();
  }

  @Override
  public void update(final UserConfirmedEvent event) {
    create
      .update(Tables.USERS)
      .set(Tables.USERS.CONFIRMED, true)
      .where(Tables.USERS.USER_ID.eq(event.getUserId().getValue()))
      .execute();
  }

  @Override
  public boolean containsUser(final Username username) {
    return create.fetchExists(create
      .selectOne()
      .from(Tables.USERS)
      .where(Tables.USERS.USERNAME.eq(username.getValue())));
  }

  @Override
  public boolean containsAnyUser() {
    return create.fetchExists(create
      .selectOne()
      .from(Tables.USERS));
  }

}
