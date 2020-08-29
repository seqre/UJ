package uj.jwzp2020.veterinaryclinic.model.serializer.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;

public class StringToLocalDateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        final String value = p.readValueAs(String.class);

        try {
            return LocalDate.parse(value);
        } catch (DateTimeException e) {
            String[] dateSplit = value.split("-");

            switch (dateSplit.length) {
                case 1:
                    return LocalDate.of(Integer.parseInt(dateSplit[0]), 1, 1);
                case 2:
                    return LocalDate.of(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]), 1);
            }
        }
        return null;
    }
}
