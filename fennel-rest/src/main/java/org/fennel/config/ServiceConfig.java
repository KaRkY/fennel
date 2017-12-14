package org.fennel.config;

import org.fennel.services.DefaultUserCheck;
import org.fennel.services.UserCheck;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

  @Bean
  public UserCheck userCheck(final DSLContext context) {
    return new DefaultUserCheck(context);
  }
}
