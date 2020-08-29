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
import uj.jwzp2020.veterinaryclinic.repository.ClientRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    private final Address address = new Address();
    private final Client a = new Client(1L, "a", "a", LocalDate.of(2020, 6, 5), Gender.MALE, address, "e", null);
    private final Client b = new Client(2L, "b", "b", LocalDate.of(2020, 6, 5), Gender.FEMALE, address, null, "t");

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Nested
    class GetClients {

        @Test
        public void returnsEmptyListForEmptyRepository() {
            given(clientRepository.findAll()).willReturn(Collections.emptyList());

            List<Client> list = clientService.getClients();

            assertThat(list).isEmpty();
        }

        @Test
        public void returnsListForNonEmptyRepository() {
            given(clientRepository.findAll()).willReturn(List.of(a, b));

            List<Client> list = clientService.getClients();

            assertThat(list).containsExactlyInAnyOrder(a, b);
        }
    }

    @Nested
    class GetClientById {

        @Test
        public void throwsExceptionOnNegativeId() {
            final Long id = -1L;
            given(clientRepository.findById(id)).willReturn(Optional.empty());

            assertThatThrownBy(() -> clientService.getClientById(id))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);
        }

        @Test
        public void throwsExceptionOnNonExistentId() {
            final Long id = 1L;
            given(clientRepository.findById(id)).willReturn(Optional.empty());

            assertThatThrownBy(() -> clientService.getClientById(id))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);
        }

        @Test
        public void returnsOnExistentId() {
            final Long id = 1L;
            given(clientRepository.findById(id)).willReturn(Optional.of(a));

            Client client = clientService.getClientById(id);

            assertThat(client.getId()).isEqualTo(a.getId());
        }
    }

    @Nested
    class Save {

        @Test
        public void returnsOnExistentId() {
            given(clientRepository.save(any(Client.class))).will(app -> app.getArgument(0));

            Client client = clientService.save(a);

            assertThat(client).isEqualTo(a);
        }
    }
}
