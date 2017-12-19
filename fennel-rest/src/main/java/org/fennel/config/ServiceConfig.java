package org.fennel.config;

import org.fennel.common.services.UserCheck;
import org.fennel.common.services.impl.DefaultUserCheck;
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
