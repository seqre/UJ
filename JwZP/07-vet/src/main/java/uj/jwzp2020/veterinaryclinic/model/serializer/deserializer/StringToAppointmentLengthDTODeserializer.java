package uj.jwzp2020.veterinaryclinic.model.serializer.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import uj.jwzp2020.veterinaryclinic.model.appointment.dto.AppointmentLengthDTO;

import java.io.IOException;

public class StringToAppointmentLengthDTODeserializer extends JsonDeserializer<AppointmentLengthDTO> {
    @Override
    public AppointmentLengthDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return AppointmentLengthDTO.of(p.readValueAs(int.class));
    }
}
