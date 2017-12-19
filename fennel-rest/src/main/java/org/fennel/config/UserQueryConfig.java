package org.fennel.config;

import org.fennel.users.query.impl.user.Handler;
import org.fennel.users.query.impl.user.Processor;
import org.fennel.users.query.impl.user.Repository;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserQueryConfig {

  @Bean
  public Handler userQueryHandler(final Repository repository) {
    return new Handler(repository);
  }

  @Bean
  public Processor userQueryProcessor(final Repository repository) {
    return new Processor(repository);
  }

  @Bean
  public Repository userQueryRepository(final DSLContext context) {
    return new Repository(context);
  }
}
