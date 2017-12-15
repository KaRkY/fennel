package org.fennel.common;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JooqUtil {

  public static SortOrder map(final Direction direction) {
    switch (direction) {
    case ASC:
      return SortOrder.ASC;

    case DESC:
      return SortOrder.DESC;

    default:
      return SortOrder.DEFAULT;
    }
  }

  public static List<SortField<?>> map(final Sort sort, final Function<String, Field<?>> mapping) {
    if (sort.isSorted()) return sort
      .stream()
      .map(field -> {
        final Field<?> mappedField = mapping.apply(field.getProperty());
        if (mappedField != null) return mappedField.sort(map(field.getDirection()));
        else return null;
      })
      .filter(d -> d != null)
      .collect(Collectors.toList());
    else return Collections.emptyList();
  }
}
