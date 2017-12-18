package org.fennel.api.common;

import java.io.Serializable;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Pageable implements Serializable {
  private static final long serialVersionUID = -681116387961543040L;

  private final int                        pageNumber;
  private final int                        pageSize;
  @Singular
  private final Map<String, SortDirection> sortProperties;

  public long getOffset() {
    return pageNumber * pageSize;
  }

  public boolean isPaged() {
    return pageSize != 0;
  }
}
