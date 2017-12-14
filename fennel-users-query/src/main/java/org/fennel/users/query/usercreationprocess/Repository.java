package org.fennel.users.query.usercreationprocess;

import java.util.List;

import org.fennel.common.Pagination;
import org.fennel.users.api.usercreationprocess.UserCreationProcessQueryObject;
import org.fennel.users.query.jooq.Tables;
import org.jooq.DSLContext;

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

  public List<UserCreationProcessQueryObject> list(final Pagination pagination) {
    final Integer pageSize = pagination != null ? pagination.getPageSize() : null;
    final Integer page = pagination != null ? pagination.getPage() : null;

    final Integer limit = pageSize != null ? pageSize : Integer.MAX_VALUE;
    final Integer offset = page == null || pageSize == null ? 0 : (page - 1) * pageSize;

    return create
      .select(
        Tables.USER_CREATION_PROCESSES.PROCESS_ID,
        Tables.USER_CREATION_PROCESSES.DISPLAY_NAME,
        Tables.USER_CREATION_PROCESSES.USERNAME,
        Tables.USER_CREATION_PROCESSES.STATE)
      .from(Tables.USER_CREATION_PROCESSES)
      .offset(offset)
      .limit(limit)
      .fetch(record -> UserCreationProcessQueryObject.builder()
        .processId(record.get(Tables.USER_CREATION_PROCESSES.PROCESS_ID))
        .displayName(record.get(Tables.USER_CREATION_PROCESSES.DISPLAY_NAME))
        .username(record.get(Tables.USER_CREATION_PROCESSES.USERNAME))
        .state(record.get(Tables.USER_CREATION_PROCESSES.STATE))
        .build());
  }
}
