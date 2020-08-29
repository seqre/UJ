package uj.jwzp2020.veterinaryclinic.model.pet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uj.jwzp2020.veterinaryclinic.model.serializer.serializer.LocalDateToStringSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    @Size(min = 2, max = 64)
    @NotBlank
    private String name;

    @JsonProperty("ownerId")
    @NotNull
    private Long ownerId;

    @JsonProperty("species")
    @NotNull
    private SpeciesDTO species;

    @JsonProperty("birthdate")
    @NotNull
    @PastOrPresent
    @JsonSerialize(using = LocalDateToStringSerializer.class)
    private LocalDate birthdate;

    @JsonProperty("deathdate")
    @PastOrPresent
    @JsonSerialize(using = LocalDateToStringSerializer.class)
    private LocalDate deathdate;
}
