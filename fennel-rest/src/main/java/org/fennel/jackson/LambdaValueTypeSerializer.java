package org.fennel.jackson;

import java.io.IOException;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LambdaValueTypeSerializer<T> extends JsonSerializer<T> {

  private final Function<T, String> valueExtractor;

  public LambdaValueTypeSerializer(final Function<T, String> valueExtractor) {
    this.valueExtractor = valueExtractor;
  }

  @Override
  public void serialize(final T value, final JsonGenerator gen, final SerializerProvider serializers)
    throws IOException {
    gen.writeString(valueExtractor.apply(value));
  }

}
