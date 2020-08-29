package uj.jwzp2020.veterinaryclinic.model.pet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SpeciesDTO {
    @JsonProperty("other") OTHER,
    @JsonProperty("dog") DOG,
    @JsonProperty("cat") CAT,
    @JsonProperty("bird") BIRD,
    @JsonProperty("rodent") RODENT,
    @JsonProperty("amphibian") AMPHIBIAN
}
