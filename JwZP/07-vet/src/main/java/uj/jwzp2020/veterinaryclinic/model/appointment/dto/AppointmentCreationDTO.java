package uj.jwzp2020.veterinaryclinic.model.appointment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uj.jwzp2020.veterinaryclinic.model.serializer.deserializer.StringToAppointmentLengthDTODeserializer;
import uj.jwzp2020.veterinaryclinic.model.serializer.deserializer.StringToLocalDateTimeDeserializer;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentCreationDTO {

    @JsonProperty("petId")
    @NotNull
    private Long petId;

    @JsonProperty("date")
    @NotNull
    @Future
    @JsonDeserialize(using = StringToLocalDateTimeDeserializer.class)
    private LocalDateTime date;

    @JsonProperty("duration")
    @NotNull
    @JsonDeserialize(using = StringToAppointmentLengthDTODeserializer.class)
    private AppointmentLengthDTO duration;

    @JsonProperty("status")
    @NotNull
    private AppointmentStatusDTO status;

    private String description = "";
}
