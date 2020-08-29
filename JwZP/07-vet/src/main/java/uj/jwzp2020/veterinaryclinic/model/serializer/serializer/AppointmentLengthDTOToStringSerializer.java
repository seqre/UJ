package uj.jwzp2020.veterinaryclinic.model.serializer.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import uj.jwzp2020.veterinaryclinic.model.appointment.dto.AppointmentLengthDTO;

import java.io.IOException;

public class AppointmentLengthDTOToStringSerializer extends JsonSerializer<AppointmentLengthDTO> {
    @Override
    public void serialize(AppointmentLengthDTO value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(value.getMinutes());
    }
}
