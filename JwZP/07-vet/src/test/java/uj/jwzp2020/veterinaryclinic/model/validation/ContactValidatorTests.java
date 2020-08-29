package uj.jwzp2020.veterinaryclinic.model.validation;

import org.junit.jupiter.api.Test;
import uj.jwzp2020.veterinaryclinic.model.client.dto.AddressDTO;
import uj.jwzp2020.veterinaryclinic.model.client.dto.ClientCreationDTO;
import uj.jwzp2020.veterinaryclinic.model.client.dto.GenderDTO;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ContactValidatorTests {

    private final AddressDTO address = new AddressDTO();
    private final ClientCreationDTO clientWithoutContact1 = new ClientCreationDTO("a", "a", LocalDate.of(2020, 6, 5), GenderDTO.MALE, address, null, null);
    private final ClientCreationDTO clientWithoutContact2 = new ClientCreationDTO("a", "a", LocalDate.of(2020, 6, 5), GenderDTO.MALE, address, "", null);
    private final ClientCreationDTO clientWithoutContact3 = new ClientCreationDTO("a", "a", LocalDate.of(2020, 6, 5), GenderDTO.MALE, address, null, "");
    private final ClientCreationDTO clientWithoutContact4 = new ClientCreationDTO("a", "a", LocalDate.of(2020, 6, 5), GenderDTO.MALE, address, "", "");
    private final ClientCreationDTO clientWithEmail = new ClientCreationDTO("a", "a", LocalDate.of(2020, 6, 5), GenderDTO.MALE, address, "e", null);
    private final ClientCreationDTO clientWithTelephone = new ClientCreationDTO("a", "a", LocalDate.of(2020, 6, 5), GenderDTO.MALE, address, null, "t");
    private final ClientCreationDTO clientWithBoth = new ClientCreationDTO("a", "a", LocalDate.of(2020, 6, 5), GenderDTO.MALE, address, "e", "t");

    private final ContactValidator contactValidator = new ContactValidator();

    @Test
    public void failsForNoContactData() {
        assertThat(contactValidator.isValid(clientWithoutContact1, null)).isFalse();
        assertThat(contactValidator.isValid(clientWithoutContact2, null)).isFalse();
        assertThat(contactValidator.isValid(clientWithoutContact3, null)).isFalse();
        assertThat(contactValidator.isValid(clientWithoutContact4, null)).isFalse();
    }

    @Test
    public void acceptsEmail() {
        assertThat(contactValidator.isValid(clientWithEmail, null)).isTrue();
    }

    @Test
    public void acceptsTelephone() {
        assertThat(contactValidator.isValid(clientWithTelephone, null)).isTrue();
    }

    @Test
    public void acceptsBoth() {
        assertThat(contactValidator.isValid(clientWithBoth, null)).isTrue();
    }
}
