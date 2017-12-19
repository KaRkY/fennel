package org.fennel.common.services;

public interface IdGeneratorService {

  String generate(String aggregateRoot);
}
