package org.fennel.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.axonframework.common.jdbc.ConnectionProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventhandling.saga.repository.jdbc.JdbcSagaStore;
import org.axonframework.eventhandling.saga.repository.jdbc.PostgresSagaSqlSchema;
import org.axonframework.eventhandling.saga.repository.jdbc.SagaSqlSchema;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventhandling.tokenstore.jdbc.JdbcTokenStore;
import org.axonframework.eventhandling.tokenstore.jdbc.PostgresTokenTableFactory;
import org.axonframework.eventhandling.tokenstore.jdbc.TokenSchema;
import org.axonframework.eventhandling.tokenstore.jdbc.TokenTableFactory;
import org.axonframework.eventsourcing.eventstore.jdbc.EventSchema;
import org.axonframework.eventsourcing.eventstore.jdbc.EventTableFactory;
import org.axonframework.eventsourcing.eventstore.jdbc.JdbcEventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jdbc.PostgresEventTableFactory;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.jdbc.SpringDataSourceConnectionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AxonConfig {

  @Bean
  public JacksonSerializer serializer(final ObjectMapper objectMapper) {
    return new JacksonSerializer(objectMapper);
  }

  @Bean
  public SpringDataSourceConnectionProvider springDataSourceConnectionProvider(final DataSource dataSource) {
    return new SpringDataSourceConnectionProvider(dataSource);
  }

  @Bean
  public JdbcEventStorageEngine eventStorageEngine(
    final Serializer serializer,
    final ConnectionProvider connectionProvider,
    final TransactionManager transactionManager) {
    return new JdbcEventStorageEngine(
      serializer,
      null,
      null,
      connectionProvider,
      transactionManager);
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
  public SagaStore<Object> sagaRepository(
    final Serializer serializer,
    final SpringDataSourceConnectionProvider dataSource) throws SQLException {
    return new JdbcSagaStore(dataSource, sagaSqlSchema(), serializer);
  }

  @Bean
  public TokenTableFactory tokenSchemaFactory() {
    return new PostgresTokenTableFactory();
  }

  @Bean
  public TokenSchema tokenSchema() {
    return new TokenSchema();
  }

  @Bean
  public TokenStore tokenStore(
    final Serializer serializer,
    final SpringDataSourceConnectionProvider dataSource) {
    return new JdbcTokenStore(dataSource, serializer);
  }
}
