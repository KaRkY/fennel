package org.fennel.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.axonframework.common.jdbc.ConnectionProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventhandling.saga.repository.jdbc.JdbcSagaStore;
import org.axonframework.eventhandling.saga.repository.jdbc.PostgresSagaSqlSchema;
import org.axonframework.eventhandling.saga.repository.jdbc.SagaSqlSchema;
import org.axonframework.eventsourcing.eventstore.jdbc.EventSchema;
import org.axonframework.eventsourcing.eventstore.jdbc.EventTableFactory;
import org.axonframework.eventsourcing.eventstore.jdbc.JdbcEventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jdbc.PostgresEventTableFactory;
import org.axonframework.spring.jdbc.SpringDataSourceConnectionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

  @Bean
  public SpringDataSourceConnectionProvider springDataSourceConnectionProvider(final DataSource dataSource) {
    return new SpringDataSourceConnectionProvider(dataSource);
  }

  @Bean
  public JdbcEventStorageEngine eventStorageEngine(
    final ConnectionProvider connectionProvider,
    final TransactionManager transactionManager) {
    final JdbcEventStorageEngine jdbcEventStorageEngine = new JdbcEventStorageEngine(connectionProvider,
      transactionManager);
    jdbcEventStorageEngine.createSchema(eventSchemaFactory());
    return jdbcEventStorageEngine;
  }

  @Bean
  public EventTableFactory eventSchemaFactory() {
    return PostgresEventTableFactory.INSTANCE;
  }

  @Bean
  public EventSchema eventSchema() {
    return new EventSchema();
  }

  @Bean
  public SagaSqlSchema sagaSqlSchema() {
    return new PostgresSagaSqlSchema();
  }

  @Bean
  public SagaStore<Object> sagaRepository(final DataSource dataSource) throws SQLException {
    final JdbcSagaStore jdbcSagaStore = new JdbcSagaStore(dataSource, sagaSqlSchema());
    // jdbcSagaStore.createSchema();
    return jdbcSagaStore;
  }
}
