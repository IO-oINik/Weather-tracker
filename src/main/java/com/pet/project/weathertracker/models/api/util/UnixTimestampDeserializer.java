package com.pet.project.weathertracker.models.api.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.*;

public class UnixTimestampDeserializer extends JsonDeserializer<OffsetDateTime> {
    @Override
    public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        long timestampInSeconds = jsonParser.getValueAsLong();
        return OffsetDateTime.ofInstant(
                Instant.ofEpochSecond(timestampInSeconds),
                ZoneId.systemDefault()
        );
    }
}
