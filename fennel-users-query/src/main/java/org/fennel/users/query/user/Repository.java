package org.fennel.users.query.user;

import java.util.List;

import org.fennel.common.Pagination;
import org.fennel.users.api.user.CreatedEvent;
import org.fennel.users.api.user.UserQueryObject;
import org.fennel.users.query.jooq.Tables;
import org.jooq.DSLContext;

public class Repository {

  private final DSLContext create;

  public Repository(final DSLContext create) {
    this.create = create;
  }

  public void insert(final CreatedEvent event) {
    create
      .insertInto(Tables.USERS)
      .columns(
        Tables.USERS.USER_ID,
        Tables.USERS.DISPLAY_NAME,
        Tables.USERS.USERNAME,
        Tables.USERS.PASSWORD,
        Tables.USERS.LOCKED)
      .values(
        event.getUserId(),
        event.getDisplayName(),
        event.getUsername(),
        event.getPassword(),
        event.isLocked())
      .execute();
  }

  public boolean containsUser(final String username) {
    return create.fetchExists(create
      .selectOne()
      .from(Tables.USERS)
      .where(Tables.USERS.USERNAME.eq(username)));
  }

  public boolean containsAnyUser() {
    return create.fetchExists(create
      .selectOne()
      .from(Tables.USERS));
  }

  public Integer count() {
    return create.fetchCount(create
      .selectOne()
      .from(Tables.USERS));
  }

  public List<UserQueryObject> list(final Pagination pagination) {
    final Integer pageSize = pagination != null ? pagination.getPageSize() : null;
    final Integer page = pagination != null ? pagination.getPage() : null;

    final Integer limit = pageSize != null ? pageSize : Integer.MAX_VALUE;
    final Integer offset = page == null || pageSize == null ? 0 : (page - 1) * pageSize;

    return create
      .select(
        Tables.USERS.USER_ID,
        Tables.USERS.DISPLAY_NAME,
        Tables.USERS.USERNAME,
        Tables.USERS.LOCKED)
      .from(Tables.USERS)
      .offset(offset)
      .limit(limit)
      .fetch(record -> UserQueryObject.builder()
        .userId(record.get(Tables.USERS.USER_ID))
        .displayName(record.get(Tables.USERS.DISPLAY_NAME))
        .username(record.get(Tables.USERS.USERNAME))
        .locked(record.get(Tables.USERS.LOCKED))
        .build());
  }

}