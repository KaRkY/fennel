package org.fennel.users.query.user;

import java.util.Optional;

import org.fennel.api.common.Page;
import org.fennel.api.common.Pageable;
import org.fennel.common.JooqUtil;
import org.fennel.users.api.user.CreatedEvent;
import org.fennel.users.api.user.UserQueryObject;
import org.fennel.users.query.jooq.Tables;
import org.jooq.DSLContext;
import org.jooq.Record4;
import org.jooq.ResultQuery;
import org.jooq.SelectSeekStepN;

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

  public Page<UserQueryObject> list(final Pageable pageable) {

    final SelectSeekStepN<Record4<String, String, String, Boolean>> select = create
      .select(
        Tables.USERS.USER_ID,
        Tables.USERS.DISPLAY_NAME,
        Tables.USERS.USERNAME,
        Tables.USERS.LOCKED)
      .from(Tables.USERS)
      .orderBy(JooqUtil.map(
        pageable.getSortProperties(),
        property -> {
          switch (property) {
          case "displayName":
            return Tables.USERS.DISPLAY_NAME;

          case "username":
            return Tables.USERS.USERNAME;

          default:
            return null;
          }
        }));

    ResultQuery<Record4<String, String, String, Boolean>> resQuery = null;
    if (pageable.isPaged()) {
      resQuery = select
        .offset((int) pageable.getOffset())
        .limit(pageable.getPageSize());
    } else {
      resQuery = select;
    }

    return Page.<UserQueryObject>builder()
      .elements(resQuery
        .fetch(record -> UserQueryObject.builder()
          .userId(record.get(Tables.USERS.USER_ID))
          .displayName(record.get(Tables.USERS.DISPLAY_NAME))
          .username(record.get(Tables.USERS.USERNAME))
          .locked(record.get(Tables.USERS.LOCKED))
          .build()))
      .total(count())
      .build();
  }

  public Optional<UserQueryObject> get(final String userId) {
    return create.select(
      Tables.USERS.USER_ID,
      Tables.USERS.DISPLAY_NAME,
      Tables.USERS.USERNAME,
      Tables.USERS.LOCKED)
      .from(Tables.USERS)
      .where(Tables.USERS.USER_ID.eq(userId))
      .fetchOptional()
      .map(record -> UserQueryObject.builder()
        .userId(record.get(Tables.USERS.USER_ID))
        .displayName(record.get(Tables.USERS.DISPLAY_NAME))
        .username(record.get(Tables.USERS.USERNAME))
        .locked(record.get(Tables.USERS.LOCKED))
        .build());
  }

}
