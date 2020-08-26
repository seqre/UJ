package pl.jwzp.seqre.DummyProject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DummyProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DummyProjectApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        /*
        // CLIENT
        TypeMap<ClientCreationDTO, Client> creationToClient = mapper.createTypeMap(ClientCreationDTO.class, Client.class);
        creationToClient.addMappings(mapping -> {
            mapping.map(ClientCreationDTO::getAddressDTO, Client::setAddress);
            mapping.map(ClientCreationDTO::getGenderDTO, Client::setGender);
        });

        TypeMap<Client, ClientResponseDTO> clientToResponse = mapper.createTypeMap(Client.class, ClientResponseDTO.class);
        clientToResponse.addMappings(mapping -> {
            mapping.map(Client::getGender, ClientResponseDTO::setGenderDTO);
            mapping.map(Client::getAddress, ClientResponseDTO::setAddressDTO);
        });
        // END CLIENT

        // PET
        TypeMap<PetCreationDTO, Pet> creationToPet = mapper.createTypeMap(PetCreationDTO.class, Pet.class);
        creationToPet.addMappings(mapping -> {
            mapping.map(PetCreationDTO::getSpecies, Pet::setSpecies);
        });

        TypeMap<Pet, PetResponseDTO> petToResponse = mapper.createTypeMap(Pet.class, PetResponseDTO.class);
        petToResponse.addMappings(mapping -> {
            mapping.map(Pet::getSpecies, PetResponseDTO::setSpecies);
        });
        // END PET

        // APPOINTMENT
        TypeMap<AppointmentCreationDTO, Appointment> creationToAppointment = mapper.createTypeMap(AppointmentCreationDTO.class, Appointment.class);
        creationToAppointment.addMappings(mapping -> {
            mapping.map(AppointmentCreationDTO::getDuration, Appointment::setDuration);
            mapping.map(AppointmentCreationDTO::getStatus, Appointment::setStatus);
        });

        TypeMap<Appointment, AppointmentResponseDTO> appointmentToResponse = mapper.createTypeMap(Appointment.class, AppointmentResponseDTO.class);
        appointmentToResponse.addMappings(mapping -> {
            mapping.map(Appointment::getDuration, AppointmentResponseDTO::setDuration);
            mapping.map(Appointment::getStatus, AppointmentResponseDTO::setStatus);
        });
        // END APPOINTMENT
        */
        return mapper;
    }
}
