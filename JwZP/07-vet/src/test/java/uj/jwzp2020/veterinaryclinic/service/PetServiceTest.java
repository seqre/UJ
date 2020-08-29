package uj.jwzp2020.veterinaryclinic.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import uj.jwzp2020.veterinaryclinic.model.client.Address;
import uj.jwzp2020.veterinaryclinic.model.client.Client;
import uj.jwzp2020.veterinaryclinic.model.client.Gender;
import uj.jwzp2020.veterinaryclinic.model.pet.Pet;
import uj.jwzp2020.veterinaryclinic.model.pet.Species;
import uj.jwzp2020.veterinaryclinic.repository.ClientRepository;
import uj.jwzp2020.veterinaryclinic.repository.PetRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    private final Pet a = new Pet(1L, "a", 1L, Species.DOG, LocalDate.of(2020, 2, 1), null);
    private final Pet b = new Pet(2L, "b", 2L, Species.CAT, LocalDate.of(2020, 2, 1), null);

    private final Client aClient = new Client(1L, "a", "a", LocalDate.of(2020, 5, 6), Gender.MALE, new Address(), "e", null);

    @Mock
    private PetRepository petRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private PetService petService;

    @Nested
    class GetPets {

        @Test
        public void returnsEmptyListForEmptyRepository() {
            given(petRepository.findAll()).willReturn(Collections.emptyList());

            List<Pet> list = petService.getPets();

            assertThat(list).isEmpty();
        }

        @Test
        public void returnsListForNonEmptyRepository() {
            given(petRepository.findAll()).willReturn(List.of(a, b));

            List<Pet> list = petService.getPets();

            assertThat(list).containsExactlyInAnyOrder(a, b);
        }
    }

    @Nested
    class GetPetById {

        @Test
        public void throwsExceptionOnNegativeId() {
            final Long id = -1L;
            given(petRepository.findById(id)).willReturn(Optional.empty());

            assertThatThrownBy(() -> petService.getPetById(id))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);
        }

        @Test
        public void throwsExceptionOnNonExistentId() {
            final Long id = 1L;
            given(petRepository.findById(id)).willReturn(Optional.empty());

            assertThatThrownBy(() -> petService.getPetById(id))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);
        }

        @Test
        public void returnsOnExistentId() {
            final Long id = 1L;
            given(petRepository.findById(id)).willReturn(Optional.of(a));

            Pet pet = petService.getPetById(id);

            assertThat(pet).isEqualTo(a);
        }
    }

    @Nested
    class Save {

        @Test
        public void throwsExceptionIfPetDoesNotExist() {
            given(clientRepository.findById(a.getOwnerId())).willReturn(Optional.empty());

            assertThatThrownBy(() -> petService.save(a))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);
        }

        @Test
        public void returnsOnExistentId() {
            given(clientRepository.findById(a.getOwnerId())).willReturn(Optional.of(aClient));
            given(petRepository.save(any(Pet.class))).will(app -> app.getArgument(0));

            Pet pet = petService.save(a);

            assertThat(pet).isEqualTo(a);
        }
    }
}
