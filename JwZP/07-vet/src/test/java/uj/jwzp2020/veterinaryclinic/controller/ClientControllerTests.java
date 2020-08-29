package uj.jwzp2020.veterinaryclinic.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;
import uj.jwzp2020.veterinaryclinic.model.client.Address;
import uj.jwzp2020.veterinaryclinic.model.client.Client;
import uj.jwzp2020.veterinaryclinic.model.client.Gender;
import uj.jwzp2020.veterinaryclinic.model.client.dto.AddressDTO;
import uj.jwzp2020.veterinaryclinic.model.client.dto.ClientCreationDTO;
import uj.jwzp2020.veterinaryclinic.model.client.dto.ClientResponseDTO;
import uj.jwzp2020.veterinaryclinic.model.client.dto.GenderDTO;
import uj.jwzp2020.veterinaryclinic.service.ClientService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTests {

    public static final int YEAR = 2020;
    public static final int MONTH = 6;
    public static final int DAY = 5;
    private final Address address = new Address();
    private final AddressDTO addressDTO = new AddressDTO();
    private final Client a = new Client(1L, "a", "a", LocalDate.of(YEAR, MONTH, DAY), Gender.MALE, address, "e", null);
    private final ClientResponseDTO aResponseDTO = new ClientResponseDTO(1L, "a", "a", LocalDate.of(YEAR, MONTH, DAY), GenderDTO.MALE, addressDTO, "e", null);
    private final Client b = new Client(2L, "b", "b", LocalDate.of(YEAR, MONTH, DAY), Gender.FEMALE, address, null, "t");
    private final ClientResponseDTO bResponseDTO = new ClientResponseDTO(2L, "b", "b", LocalDate.of(YEAR, MONTH, DAY), GenderDTO.FEMALE, addressDTO, null, "t");

    private MockMvc mockMvc;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController)
                .build();
    }

    @Nested
    class GetClients {

        @Test
        public void returnsEmptyListForEmptyDatabase() throws Exception {
            given(clientService.getClients()).willReturn(Collections.emptyList());

            MockHttpServletResponse response = mockMvc.perform(
                    get("/clients")
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentLength()).isEqualTo(0);
        }

        @Test
        public void returnsOnePersonInListForOnePersonInDatabase() throws Exception {
            given(clientService.getClients()).willReturn(List.of(a));
            given(modelMapper.map(a, ClientResponseDTO.class)).willReturn(aResponseDTO);

            MockHttpServletResponse response = mockMvc.perform(
                    get("/clients")
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentAsString()).contains("1", "a", "male", "e");
        }

        @Test
        public void returnsPeopleInListForPeopleInDatabase() throws Exception {
            given(clientService.getClients()).willReturn(List.of(a, b));
            given(modelMapper.map(a, ClientResponseDTO.class)).willReturn(aResponseDTO);
            given(modelMapper.map(b, ClientResponseDTO.class)).willReturn(bResponseDTO);

            MockHttpServletResponse response = mockMvc.perform(
                    get("/clients")
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentAsString()).contains("1", "a", "male", "e", "2", "b", "female", "t");
        }
    }

    @Nested
    class GetClientById {

        @Test
        public void throwsExceptionOnNegativeId() throws Exception {
            given(clientService.getClientById(-1L)).willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

            MockHttpServletResponse response = mockMvc.perform(
                    get("/clients/-1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void throwsExceptionOnNonExistentId() throws Exception {
            given(clientService.getClientById(1L)).willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

            MockHttpServletResponse response = mockMvc.perform(
                    get("/clients/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void returnsOnExistentId() throws Exception {
            given(clientService.getClientById(1L)).willReturn(a);
            given(modelMapper.map(a, ClientResponseDTO.class)).willReturn(aResponseDTO);

            MockHttpServletResponse response = mockMvc.perform(
                    get("/clients/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentAsString()).contains("1", "a", "male", "e");
        }

    }

    @Nested
    class CreateClient {

        private final Client aProperDate = new Client(1L, "a", "a", LocalDate.of(YEAR, MONTH, DAY), Gender.MALE, address, "e", null);
        private final ClientResponseDTO aProperDateDTO = new ClientResponseDTO(1L, "a", "a", LocalDate.of(YEAR, MONTH, DAY), GenderDTO.MALE, addressDTO, "e", null);
        private final Client aDateWithoutDay = new Client(1L, "a", "a", LocalDate.of(YEAR, MONTH, 1), Gender.MALE, address, "e", null);
        private final ClientResponseDTO aDateWithoutDayDTO = new ClientResponseDTO(1L, "a", "a", LocalDate.of(YEAR, MONTH, 1), GenderDTO.MALE, addressDTO, "e", null);
        private final Client aDateOnlyYear = new Client(1L, "a", "a", LocalDate.of(YEAR, 1, 1), Gender.MALE, address, "e", null);
        private final ClientResponseDTO aDateOnlyYearDTO = new ClientResponseDTO(1L, "a", "a", LocalDate.of(YEAR, 1, 1), GenderDTO.MALE, addressDTO, "e", null);

        private String creationStringTemplate = "{\n" +
                "    \"firstName\": \"a\",\n" +
                "    \"lastName\": \"a\",\n" +
                "    \"birthdate\": \"%s\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"Kwiatkowa\",\n" +
                "        \"parcelNumber\": 3,\n" +
                "        \"flatNumber\": 6,\n" +
                "        \"city\": \"Wawa\",\n" +
                "        \"zipcode\": \"12-345\",\n" +
                "        \"country\": \"Polska\"\n" +
                "    },\n" +
                "    \"email\": \"example@example.com\",\n" +
                "    \"telephoneNumber\": \"123456789\"\n" +
                "}";


        @Test
        public void successfulClientCreationWithProperDate() throws Exception {
            given(modelMapper.map(any(ClientCreationDTO.class), eq(Client.class))).willReturn(aProperDate);
            given(clientService.save(any(Client.class))).will(obj -> obj.getArgument(0));
            given(modelMapper.map(any(Client.class), eq(ClientResponseDTO.class))).willReturn(aProperDateDTO);

            MockHttpServletResponse response = mockMvc.perform(
                    post("/clients")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(String.format(creationStringTemplate, "2020-06-05"))
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(response.getContentAsString()).contains("1", "a", "male", "e", "2020-06-05");
        }

        @Test
        public void successfulClientCreationWithoutDay() throws Exception {
            given(modelMapper.map(any(ClientCreationDTO.class), eq(Client.class))).willReturn(aDateWithoutDay);
            given(clientService.save(any(Client.class))).will(obj -> obj.getArgument(0));
            given(modelMapper.map(any(Client.class), eq(ClientResponseDTO.class))).willReturn(aDateWithoutDayDTO);

            MockHttpServletResponse response = mockMvc.perform(
                    post("/clients")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(String.format(creationStringTemplate, "2020-06"))
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(response.getContentAsString()).contains("1", "a", "male", "e", "2020-06-01");
        }

        @Test
        public void successfulClientCreationOnlyYear() throws Exception {
            given(modelMapper.map(any(ClientCreationDTO.class), eq(Client.class))).willReturn(aDateOnlyYear);
            given(clientService.save(any(Client.class))).will(obj -> obj.getArgument(0));
            given(modelMapper.map(any(Client.class), eq(ClientResponseDTO.class))).willReturn(aDateOnlyYearDTO);

            MockHttpServletResponse response = mockMvc.perform(
                    post("/clients")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(String.format(creationStringTemplate, "2020"))
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(response.getContentAsString()).contains("1", "a", "male", "e", "2020-01-01");
        }

    }
}
