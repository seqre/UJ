package uj.jwzp2020.veterinaryclinic.model.appointment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentChangeDataDTO {

    @JsonProperty("description")
    @Size(max = 1536)
    private String description;

    @JsonProperty("status")
    private AppointmentStatusDTO status;

}
