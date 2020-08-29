package uj.jwzp2020.veterinaryclinic.model.appointment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AppointmentStatusDTO {
    @JsonProperty("scheduled") SCHEDULED,
    @JsonProperty("done") DONE,
    @JsonProperty("client not arrived") CLIENT_NOT_ARRIVED
}
