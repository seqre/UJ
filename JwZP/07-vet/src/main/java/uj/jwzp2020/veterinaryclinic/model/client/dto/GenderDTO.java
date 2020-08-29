package uj.jwzp2020.veterinaryclinic.model.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum GenderDTO {
    @JsonProperty("male") MALE,
    @JsonProperty("female") FEMALE
}
