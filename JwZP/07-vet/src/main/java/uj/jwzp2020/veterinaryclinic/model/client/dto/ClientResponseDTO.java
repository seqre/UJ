package uj.jwzp2020.veterinaryclinic.model.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uj.jwzp2020.veterinaryclinic.model.serializer.serializer.LocalDateToStringSerializer;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDTO {

    @JsonProperty("id")
    @NotNull
    private Long id;

    @JsonProperty("firstName")
    @Size(min = 3, max = 64)
    @NotBlank
    private String firstName;

    @JsonProperty("lastName")
    @Size(min = 2, max = 64)
    @NotBlank
    private String lastName;

    @JsonProperty("birthdate")
    @NotNull
    @Past
    @JsonSerialize(using = LocalDateToStringSerializer.class)
    private LocalDate birthdate;

    @JsonProperty("gender")
    @NotNull
    private GenderDTO genderDTO;

    @JsonProperty("address")
    @NotNull
    private AddressDTO addressDTO;

    @JsonProperty("email")
    @Email
    private String email;

    @JsonProperty("telephoneNumber")
    private String telephoneNumber;
}
