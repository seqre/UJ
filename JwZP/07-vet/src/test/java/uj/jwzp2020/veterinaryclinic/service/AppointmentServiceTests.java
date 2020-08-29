package uj.jwzp2020.veterinaryclinic.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import uj.jwzp2020.veterinaryclinic.model.appointment.Appointment;
import uj.jwzp2020.veterinaryclinic.model.appointment.AppointmentLength;
import uj.jwzp2020.veterinaryclinic.model.appointment.AppointmentStatus;
import uj.jwzp2020.veterinaryclinic.model.appointment.dto.AppointmentChangeDataDTO;
import uj.jwzp2020.veterinaryclinic.model.appointment.dto.AppointmentStatusDTO;
import uj.jwzp2020.veterinaryclinic.model.pet.Pet;
import uj.jwzp2020.veterinaryclinic.model.pet.Species;
import uj.jwzp2020.veterinaryclinic.repository.AppointmentRepository;
import uj.jwzp2020.veterinaryclinic.repository.PetRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTests {

    private final static LocalDateTime dummyDateTime = LocalDateTime.of(2020, 6, 5, 10, 0);
    private final static Appointment firstAppointment = new Appointment(1L, 22L, dummyDateTime, AppointmentLength.SIXTY_MINUTES, AppointmentStatus.SCHEDULED, "d");
    private static Appointment secondAppointment;
    private final Pet a = new Pet(22L, "a", 1L, Species.DOG, LocalDate.of(2020, 2, 1), null);

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        secondAppointment = new Appointment(2L, 44L, dummyDateTime.plusDays(1), AppointmentLength.FIFTEEN_MINUTES, AppointmentStatus.DONE, "b");
    }

    @Nested
    class GetAppointments {

        @Test
        public void returnsEmptyListForEmptyRepository() {
            given(appointmentRepository.findAll()).willReturn(Collections.emptyList());

            List<Appointment> list = appointmentService.getAppointments();

            assertThat(list).isEmpty();
        }

        @Test
        public void returnsListForNonEmptyRepository() {
            given(appointmentRepository.findAll()).willReturn(List.of(firstAppointment, secondAppointment));

            List<Appointment> list = appointmentService.getAppointments();

            assertThat(list).containsExactlyInAnyOrder(firstAppointment, secondAppointment);
        }
    }

    @Nested
    class GetAppointmentById {

        @Test
        public void throwsExceptionOnNegativeId() {
            final Long id = -1L;
            given(appointmentRepository.findById(id)).willReturn(Optional.empty());

            assertThatThrownBy(() -> appointmentService.getAppointmentById(id))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);
        }

        @Test
        public void throwsExceptionOnNonExistentId() {
            final Long id = 1L;
            given(appointmentRepository.findById(id)).willReturn(Optional.empty());

            assertThatThrownBy(() -> appointmentService.getAppointmentById(id))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);
        }

        @Test
        public void returnsOnExistentId() {
            final Long id = 1L;
            given(appointmentRepository.findById(id)).willReturn(Optional.of(firstAppointment));

            Appointment appointment = appointmentService.getAppointmentById(id);

            assertThat(appointment.getId()).isEqualTo(firstAppointment.getId());
        }
    }

    @Nested
    class Save {

        @Test
        public void throwsExceptionIfPetDoesNotExist() {
            assertThatThrownBy(() -> appointmentService.save(firstAppointment))
                    .isInstanceOf(ResponseStatusException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);
        }

        @Test
        public void returnsOnExistentId() {
            given(petRepository.findById(firstAppointment.getPetId())).willReturn(Optional.of(a));
            given(appointmentRepository.save(any(Appointment.class))).will(app -> app.getArgument(0));

            Appointment appointment = appointmentService.save(firstAppointment);

            assertThat(appointment.getId()).isEqualTo(firstAppointment.getId());
        }
    }

    @Nested
    class ChangeData {

        @Test
        void returnsUnchangedAppointmentForNullDTO() {
            AppointmentChangeDataDTO dto = new AppointmentChangeDataDTO(null, null);

            Appointment result = appointmentService.changeData(firstAppointment, dto);

            assertThat(result).isEqualTo(firstAppointment);
        }

        @Test
        void returnsAppointmentWithDifferentDescription() {
            AppointmentChangeDataDTO dto = new AppointmentChangeDataDTO("new", null);

            Appointment result = appointmentService.changeData(secondAppointment, dto);

            assertThat(result.getDescription()).isEqualTo(dto.getDescription());
        }

        @Test
        void returnsAppointmentWithDifferentStatus() {
            AppointmentChangeDataDTO dto = new AppointmentChangeDataDTO(null, AppointmentStatusDTO.DONE);
            given(modelMapper.map(dto.getStatus(), AppointmentStatus.class)).willReturn(AppointmentStatus.DONE);

            Appointment result = appointmentService.changeData(secondAppointment, dto);

            assertThat(result.getStatus().name()).isEqualTo(dto.getStatus().name());
        }
    }

    @Nested
    class GetCollidingCount {
        public static final int YEAR = 2020;
        public static final int MONTH = 6;
        public static final int DAY = 5;
        private final LocalDateTime dummyDateTime = LocalDateTime.of(YEAR, MONTH, DAY, 8, 0);

        private final Appointment appointment8_9 = new Appointment(1L, 22L, dummyDateTime, AppointmentLength.SIXTY_MINUTES, AppointmentStatus.SCHEDULED, "d");
        private final Appointment appointment9_15_9_30 = new Appointment(2L, 44L, dummyDateTime.plusHours(1).plusMinutes(15), AppointmentLength.FIFTEEN_MINUTES, AppointmentStatus.SCHEDULED, "b");
        private final Appointment appointment10_10_30 = new Appointment(3L, 66L, dummyDateTime.plusHours(2), AppointmentLength.THIRTY_MINUTES, AppointmentStatus.SCHEDULED, "b");
        private final Appointment appointmentDone = new Appointment(4L, 88L, dummyDateTime.plusHours(2), AppointmentLength.FIFTEEN_MINUTES, AppointmentStatus.DONE, "b");
        private final Appointment appointmentOverlapping = new Appointment(5L, 1010L, dummyDateTime.plusHours(1), AppointmentLength.SIXTY_MINUTES, AppointmentStatus.SCHEDULED, "b");
        private final Appointment appointmentSquezzing = new Appointment(5L, 1010L, dummyDateTime.plusHours(1).plusMinutes(30), AppointmentLength.THIRTY_MINUTES, AppointmentStatus.SCHEDULED, "b");

        @BeforeEach
        public void setUp() {
            given(appointmentRepository.findAll()).willReturn(List.of(appointment8_9, appointment9_15_9_30, appointment10_10_30, appointmentDone));
        }

        @Test
        public void failsWhenAddExistent() {
            long colliding = appointmentService.getCollidingCount(appointment8_9.getDate(), appointment8_9.getDate().plusMinutes(appointment8_9.getDuration().getMinutes()));

            assertThat(colliding).isGreaterThan(0);
        }

        @Test
        public void failsWhenAddOverlapping() {
            long colliding = appointmentService.getCollidingCount(appointmentOverlapping.getDate(), appointmentOverlapping.getDate().plusMinutes(appointmentOverlapping.getDuration().getMinutes()));

            assertThat(colliding).isGreaterThan(0);
        }

        @Test
        public void worksWhenSquezzingIn() {
            long colliding = appointmentService.getCollidingCount(appointmentSquezzing.getDate(), appointmentSquezzing.getDate().plusMinutes(appointmentSquezzing.getDuration().getMinutes()));

            assertThat(colliding).isEqualTo(0);
        }
    }
}
