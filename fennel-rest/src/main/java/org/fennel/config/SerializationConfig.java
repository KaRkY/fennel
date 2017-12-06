package org.fennel.config;

import org.fennel.api.users.Password;
import org.fennel.api.users.UserId;
import org.fennel.api.users.UserPin;
import org.fennel.api.users.Username;
import org.fennel.jackson.LambdaValueTypeDeserializer;
import org.fennel.jackson.LambdaValueTypeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Configuration
public class SerializationConfig {

  @Bean
  public Module customModule() {
    final SimpleModule module = new SimpleModule();
    module.addSerializer(UserId.class, new LambdaValueTypeSerializer<>(UserId::toString));
    module.addDeserializer(UserId.class, new LambdaValueTypeDeserializer<>(UserId::of));

    module.addSerializer(Username.class, new LambdaValueTypeSerializer<>(Username::toString));
    module.addDeserializer(Username.class, new LambdaValueTypeDeserializer<>(Username::of));

    module.addSerializer(Password.class, new LambdaValueTypeSerializer<>(Password::toString));
    module.addDeserializer(Password.class, new LambdaValueTypeDeserializer<>(Password::of));

    module.addSerializer(UserPin.class, new LambdaValueTypeSerializer<>(UserPin::toString));
    module.addDeserializer(UserPin.class, new LambdaValueTypeDeserializer<>(UserPin::of));
    return module;
  }
}
