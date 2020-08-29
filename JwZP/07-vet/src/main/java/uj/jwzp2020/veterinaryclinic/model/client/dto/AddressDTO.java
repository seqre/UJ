package uj.jwzp2020.veterinaryclinic.model.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    @JsonProperty("street")
    @Size(min = 3, max = 96)
    private String street;

    @JsonProperty("parcelNumber")
    @NotNull
    private Integer parcelNumber;

    @JsonProperty("flatNumber")
    private Integer flatNumber;

    @JsonProperty("city")
    @Size(min = 2, max = 64)
    @NotBlank
    private String city;

    @JsonProperty("zipcode")
    @Size(min = 3, max = 8)
    @NotBlank
    private String zipcode;

    @JsonProperty("country")
    @Size(min = 4, max = 64)
    @NotBlank
    private String country;

}
