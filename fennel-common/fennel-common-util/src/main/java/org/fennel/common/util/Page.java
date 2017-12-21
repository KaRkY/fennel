package org.fennel.common.util;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Builder
@Wither
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Page<T>
  implements Serializable {
  private static final long serialVersionUID = 7935062263331123871L;

  @Singular
  private final List<T>  elements;
  private final Pageable pageable;
  private final int      total;

  public List<T> getContent() {
    return getElements();
  }

  public int getNumber() {
    return pageable.isPaged() ? pageable.getPageNumber() : 0;
  }

  public int getSize() {
    return pageable.isPaged() ? pageable.getPageSize() : 0;
  }

  public int getTotalPages() {
    return getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
  }

  public long getTotalSize() {
    return total;
  }
}
