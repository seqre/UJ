package uj.jwzp2020.veterinaryclinic.model.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uj.jwzp2020.veterinaryclinic.model.serializer.deserializer.StringToLocalDateDeserializer;
import uj.jwzp2020.veterinaryclinic.model.validation.Contact;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@Contact
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientCreationDTO {

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
    @JsonDeserialize(using = StringToLocalDateDeserializer.class)
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
    @Pattern(regexp = "[0-9]{9}")
    private String telephoneNumber;
}
