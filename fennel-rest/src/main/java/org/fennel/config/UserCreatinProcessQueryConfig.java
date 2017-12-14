package org.fennel.config;

import org.fennel.users.query.usercreationprocess.Handler;
import org.fennel.users.query.usercreationprocess.Processor;
import org.fennel.users.query.usercreationprocess.Repository;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCreatinProcessQueryConfig {

  @Bean
  public Handler userCreationProcessQueryHandler(final Repository repository) {
    return new Handler(repository);
  }

  @Bean
  public Processor userCreationProcessQueryProcessor(final Repository repository) {
    return new Processor(repository);
  }

  @Bean
  public Repository userCreationProcessQueryRepository(final DSLContext context) {
    return new Repository(context);
  }
}
