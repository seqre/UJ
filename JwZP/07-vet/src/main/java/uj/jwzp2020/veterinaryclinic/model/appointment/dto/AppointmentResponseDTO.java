package uj.jwzp2020.veterinaryclinic.model.appointment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uj.jwzp2020.veterinaryclinic.model.serializer.serializer.AppointmentLengthDTOToStringSerializer;
import uj.jwzp2020.veterinaryclinic.model.serializer.serializer.LocalDateTimeToStringSerializer;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("petId")
    @NotNull
    private Long petId;

    @JsonProperty("date")
    @NotNull
    @Future
    @JsonSerialize(using = LocalDateTimeToStringSerializer.class)
    private LocalDateTime date;

    @JsonProperty("duration")
    @NotNull
    @JsonSerialize(using = AppointmentLengthDTOToStringSerializer.class)
    private AppointmentLengthDTO duration;

    @JsonProperty("status")
    @NotNull
    private AppointmentStatusDTO status;

    @JsonProperty("description")
    @Size(max = 1536)
    private String description;
}
