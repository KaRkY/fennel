package org.fennel.common;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.fennel.api.common.SortDirection;
import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.SortOrder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JooqUtil {

  public static SortOrder map(final SortDirection direction) {
    switch (direction) {
    case ASC:
      return SortOrder.ASC;

    case DESC:
      return SortOrder.DESC;

    case DEFAULT:
      return SortOrder.DEFAULT;

    default:
      return SortOrder.DEFAULT;
    }
  }

  public static List<SortField<?>> map(final Map<String, SortDirection> sortProperties, final Function<String, Field<?>> mapping) {
    if (!sortProperties.isEmpty()) return sortProperties
      .entrySet()
      .stream()
      .map(entry -> {
        final Field<?> mappedField = mapping.apply(entry.getKey());
        if (mappedField != null) return mappedField.sort(map(entry.getValue()));
        else return null;
      })
      .filter(d -> d != null)
      .collect(Collectors.toList());
    else return Collections.emptyList();
  }
}
