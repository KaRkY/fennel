package org.fennel.users.query.usercreationprocess;

import java.util.Optional;

import org.fennel.common.JooqUtil;
import org.fennel.users.api.usercreationprocess.UserCreationProcessQueryObject;
import org.fennel.users.query.jooq.Tables;
import org.jooq.DSLContext;
import org.jooq.Record5;
import org.jooq.ResultQuery;
import org.jooq.SelectSeekStepN;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class Repository {

  private final DSLContext create;

  public Repository(final DSLContext create) {
    this.create = create;
  }

  public void insert(final UserCreationProcessQueryObject event) {
    create
      .insertInto(Tables.USER_CREATION_PROCESSES)
      .columns(
        Tables.USER_CREATION_PROCESSES.PROCESS_ID,
        Tables.USER_CREATION_PROCESSES.DISPLAY_NAME,
        Tables.USER_CREATION_PROCESSES.USERNAME,
        Tables.USER_CREATION_PROCESSES.PASSWORD,
        Tables.USER_CREATION_PROCESSES.STATE)
      .values(
        event.getProcessId(),
        event.getDisplayName(),
        event.getUsername(),
        event.getPassword(),
        event.getState())
      .execute();
  }

  public void updateState(final String processId, final String state) {
    create
      .update(Tables.USER_CREATION_PROCESSES)
      .set(Tables.USER_CREATION_PROCESSES.STATE, state)
      .where(Tables.USER_CREATION_PROCESSES.PROCESS_ID.eq(processId))
      .execute();
  }

  public Integer count() {
    return create.fetchCount(create
      .selectOne()
      .from(Tables.USER_CREATION_PROCESSES));
  }

  public Page<UserCreationProcessQueryObject> list(final Pageable pageable) {
    final SelectSeekStepN<Record5<String, String, String, String, String>> select = create.select(
      Tables.USER_CREATION_PROCESSES.PROCESS_ID,
      Tables.USER_CREATION_PROCESSES.DISPLAY_NAME,
      Tables.USER_CREATION_PROCESSES.USERNAME,
      Tables.USER_CREATION_PROCESSES.PASSWORD,
      Tables.USER_CREATION_PROCESSES.STATE)
      .from(Tables.USER_CREATION_PROCESSES)
      .orderBy(JooqUtil.map(
        pageable.getSort(),
        property -> {
          switch (property) {
          case "displayName":
            return Tables.USER_CREATION_PROCESSES.DISPLAY_NAME;

          case "username":
            return Tables.USER_CREATION_PROCESSES.USERNAME;

          case "state":
            return Tables.USER_CREATION_PROCESSES.STATE;

          default:
            return null;
          }
        }));

    ResultQuery<Record5<String, String, String, String, String>> resQuery = null;
    if (pageable.isPaged()) {
      resQuery = select
        .offset((int) pageable.getOffset())
        .limit(pageable.getPageSize());
    } else {
      resQuery = select;
    }

    return new PageImpl<>(resQuery
      .fetch(record -> UserCreationProcessQueryObject.builder()
        .processId(record.get(Tables.USER_CREATION_PROCESSES.PROCESS_ID))
        .displayName(record.get(Tables.USER_CREATION_PROCESSES.DISPLAY_NAME))
        .username(record.get(Tables.USER_CREATION_PROCESSES.USERNAME))
        .password(record.get(Tables.USER_CREATION_PROCESSES.PASSWORD))
        .state(record.get(Tables.USER_CREATION_PROCESSES.STATE))
        .build()),
      pageable, count());
  }

  public Optional<UserCreationProcessQueryObject> get(final String processId) {
    return create.select(
      Tables.USER_CREATION_PROCESSES.PROCESS_ID,
      Tables.USER_CREATION_PROCESSES.DISPLAY_NAME,
      Tables.USER_CREATION_PROCESSES.USERNAME,
      Tables.USER_CREATION_PROCESSES.PASSWORD,
      Tables.USER_CREATION_PROCESSES.STATE)
      .from(Tables.USER_CREATION_PROCESSES)
      .where(Tables.USER_CREATION_PROCESSES.PROCESS_ID.eq(processId))
      .fetchOptional()
      .map(record -> UserCreationProcessQueryObject.builder()
        .processId(record.get(Tables.USER_CREATION_PROCESSES.PROCESS_ID))
        .displayName(record.get(Tables.USER_CREATION_PROCESSES.DISPLAY_NAME))
        .username(record.get(Tables.USER_CREATION_PROCESSES.USERNAME))
        .password(record.get(Tables.USER_CREATION_PROCESSES.PASSWORD))
        .state(record.get(Tables.USER_CREATION_PROCESSES.STATE))
        .build());
  }
}
