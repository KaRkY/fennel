package org.fennel.common.services.impl;

import java.util.UUID;

import org.fennel.common.services.IdGeneratorService;

public class DefaultIdGeneratorService implements IdGeneratorService {

  @Override
  public String generate(final String aggregateRoot) {
    return UUID.randomUUID().toString();
  }

}
