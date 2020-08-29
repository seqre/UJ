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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;
import uj.jwzp2020.veterinaryclinic.model.appointment.Appointment;
import uj.jwzp2020.veterinaryclinic.model.appointment.AppointmentLength;
import uj.jwzp2020.veterinaryclinic.model.appointment.AppointmentStatus;
import uj.jwzp2020.veterinaryclinic.model.appointment.dto.*;
import uj.jwzp2020.veterinaryclinic.service.AppointmentService;
import uj.jwzp2020.veterinaryclinic.utils.TimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentControllerTests {

    private final static LocalTime START = (LocalTime) ReflectionTestUtils.getField(AppointmentController.class, "START");
    private final static LocalTime END = (LocalTime) ReflectionTestUtils.getField(AppointmentController.class, "END");
    public static final int YEAR = 2020;
    public static final int MONTH = 6;
    public static final int DAY = 5;
    private final static LocalDateTime dummyDateTime = LocalDateTime.of(YEAR, MONTH, DAY, 10, 0);

    private final static Appointment firstAppointment = new Appointment(1L, 22L, dummyDateTime, AppointmentLength.SIXTY_MINUTES, AppointmentStatus.SCHEDULED, "d");
    private final static AppointmentResponseDTO firstAppointmentDTO = new AppointmentResponseDTO(1L, 22L, dummyDateTime, AppointmentLengthDTO.SIXTY_MINUTES, AppointmentStatusDTO.SCHEDULED, "d");

    private final static Appointment secondAppointment = new Appointment(2L, 44L, dummyDateTime.plusDays(1), AppointmentLength.FIFTEEN_MINUTES, AppointmentStatus.DONE, "b");
    private final static AppointmentResponseDTO secondAppointmentDTO = new AppointmentResponseDTO(2L, 44L, dummyDateTime.plusDays(1), AppointmentLengthDTO.FIFTEEN_MINUTES, AppointmentStatusDTO.DONE, "b");

    private MockMvc mockMvc;

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TimeUtils timeUtils;

    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    private void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController)
                .build();
    }

    @Nested
    class GetAppointments {

        @Nested
        class NoDate {

            @Test
            public void returnsEmptyListForEmptyDatabase() throws Exception {
                given(appointmentService.getAppointments()).willReturn(Collections.emptyList());

                MockHttpServletResponse response = mockMvc.perform(
                        get("/appointments")
                                .accept(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
                assertThat(response.getContentLength()).isEqualTo(0);
            }


            @Test
            public void returnsOneAppointmentInListForOneAppointmentInDatabase() throws Exception {
                given(appointmentService.getAppointments()).willReturn(List.of(firstAppointment));
                given(modelMapper.map(firstAppointment, AppointmentResponseDTO.class)).willReturn(firstAppointmentDTO);

                MockHttpServletResponse response = mockMvc.perform(
                        get("/appointments")
                                .accept(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
                assertThat(response.getContentAsString()).contains("1", "22", "scheduled");
            }

            @Test
            public void returnsMultipleAppointmentsInListForMultipleAppointmentsInDatabase() throws Exception {
                given(appointmentService.getAppointments()).willReturn(List.of(firstAppointment, secondAppointment));
                given(modelMapper.map(firstAppointment, AppointmentResponseDTO.class)).willReturn(firstAppointmentDTO);
                given(modelMapper.map(secondAppointment, AppointmentResponseDTO.class)).willReturn(secondAppointmentDTO);

                MockHttpServletResponse response = mockMvc.perform(
                        get("/appointments")
                                .accept(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
                assertThat(response.getContentAsString()).contains(
                        "1", "22", "scheduled",
                        "2", "44", "done"
                );
            }
        }

        @Nested
        class WithDate {

            private final static String wrongDate = "324-42-3";
            private final static String properDate = "2020-06-05";


            @Test
            public void throwsExceptionForWrongDate() throws Exception {
                given(appointmentService.getAppointments()).willReturn(Collections.emptyList());

                MockHttpServletResponse response = mockMvc.perform(
                        get("/appointments")
                                .param("day", wrongDate)
                                .accept(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            }

            @Test
            public void worksNormallyForEmptyStringAsDate() throws Exception {
                given(appointmentService.getAppointments()).willReturn(List.of(firstAppointment));
                given(modelMapper.map(firstAppointment, AppointmentResponseDTO.class)).willReturn(firstAppointmentDTO);

                MockHttpServletResponse response = mockMvc.perform(
                        get("/appointments")
                                .param("day", "")
                                .accept(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
                assertThat(response.getContentAsString()).contains("1", "22", "scheduled");
            }

            @Test
            public void returnsEmptyListForAppointmentsOnOtherDays() throws Exception {
                given(appointmentService.getAppointments()).willReturn(List.of(secondAppointment));

                MockHttpServletResponse response = mockMvc.perform(
                        get("/appointments")
                                .param("day", properDate)
                                .accept(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
                assertThat(response.getContentLength()).isEqualTo(0);
            }


            @Test
            public void returnsListForAppointmentsOnSpecifiedDay() throws Exception {
                given(appointmentService.getAppointments()).willReturn(List.of(firstAppointment, secondAppointment));
                given(modelMapper.map(firstAppointment, AppointmentResponseDTO.class)).willReturn(firstAppointmentDTO);

                MockHttpServletResponse response = mockMvc.perform(
                        get("/appointments")
                                .param("day", properDate)
                                .accept(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
                assertThat(response.getContentAsString()).contains("1", "22", "scheduled");
            }
        }

    }

    @Nested
    class GetAppointmentById {

        @Test
        public void throwsExceptionOnNegativeId() throws Exception {
            given(appointmentService.getAppointmentById(-1L)).willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

            MockHttpServletResponse response = mockMvc.perform(
                    get("/appointments/-1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void throwsExceptionOnNonExistentId() throws Exception {
            given(appointmentService.getAppointmentById(1L)).willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

            MockHttpServletResponse response = mockMvc.perform(
                    get("/appointments/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void returnsOnExistentId() throws Exception {
            given(appointmentService.getAppointmentById(1L)).willReturn(firstAppointment);
            given(modelMapper.map(firstAppointment, AppointmentResponseDTO.class)).willReturn(firstAppointmentDTO);

            MockHttpServletResponse response = mockMvc.perform(
                    get("/appointments/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentAsString()).contains("1", "22", "scheduled");
        }

    }

    @Nested
    class ChangeAppointmentDataById {

        private final String nullChangeData = "{}";
        private final String descriptionAndStatusChangeData = "{\"description\":\"new\",\"status\":\"done\"}";

        @Test
        public void throwsExceptionOnNonExistentId() throws Exception {
            given(appointmentService.getAppointmentById(1L)).willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

            MockHttpServletResponse response = mockMvc.perform(
                    patch("/appointments/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(nullChangeData)
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void willNotChangeForProvidedNullValues() throws Exception {
            given(appointmentService.getAppointmentById(1L)).willReturn(firstAppointment);
            given(appointmentService.save(firstAppointment)).willReturn(firstAppointment);
            given(modelMapper.map(firstAppointment, AppointmentResponseDTO.class)).willReturn(firstAppointmentDTO);

            MockHttpServletResponse response = mockMvc.perform(
                    patch("/appointments/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(nullChangeData)
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentAsString()).contains("1", "22", "scheduled", "d");
        }


        @Test
        public void willChangeData() throws Exception {
            Appointment firstAppointmentWithChangedData =
                    new Appointment(1L, 22L, dummyDateTime, AppointmentLength.SIXTY_MINUTES, AppointmentStatus.DONE, "new");
            AppointmentResponseDTO firstAppointmentWithChangedDataDTO =
                    new AppointmentResponseDTO(1L, 22L, dummyDateTime, AppointmentLengthDTO.SIXTY_MINUTES, AppointmentStatusDTO.DONE, "new");

            given(appointmentService.getAppointmentById(1L)).willReturn(firstAppointment);
            given(appointmentService.changeData(eq(firstAppointment), any(AppointmentChangeDataDTO.class))).willReturn(firstAppointmentWithChangedData);
            given(appointmentService.save(any(Appointment.class))).will(obj -> obj.getArgument(0));
            given(modelMapper.map(any(Appointment.class), eq(AppointmentResponseDTO.class))).willReturn(firstAppointmentWithChangedDataDTO);

            MockHttpServletResponse response = mockMvc.perform(
                    patch("/appointments/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(descriptionAndStatusChangeData)
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentAsString()).contains("1", "22", "done", "new");
        }
    }

    @Nested
    class CreateAppointment {
        private final String creationString = "{\"petId\": 22,\n" +
                "\t\"date\": \"2020-05-05T10:00:00\",\n" +
                "\t\"duration\": 60,\n" +
                "\t\"status\": \"scheduled\"}";
        private final AppointmentCreationDTO firstAppointmentCreationDTO =
                new AppointmentCreationDTO(22L, LocalDateTime.of(YEAR, MONTH, DAY, 10, 0), AppointmentLengthDTO.SIXTY_MINUTES, AppointmentStatusDTO.SCHEDULED, "");

        @Test
        public void throwsErrorWhenForTodayOrPast() throws Exception {
            given(modelMapper.map(any(AppointmentCreationDTO.class), eq(Appointment.class))).willReturn(firstAppointment);
            given(timeUtils.getNow()).willReturn(LocalDate.of(YEAR, MONTH, DAY));

            MockHttpServletResponse response = mockMvc.perform(
                    post("/appointments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(creationString)
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
            assertThat(response.getErrorMessage()).contains("Visits may be arranged from the following day onwards");
        }

        @Test
        public void throwsErrorWhenBeforeClinicWorkingHours() throws Exception {
            Appointment appointmentBefore =
                    new Appointment(1L, 22L, LocalDateTime.of(YEAR, MONTH, DAY, 7, 59), AppointmentLength.SIXTY_MINUTES, AppointmentStatus.SCHEDULED, "");
            AppointmentCreationDTO appointmentBeforeDTO =
                    new AppointmentCreationDTO(22L, LocalDateTime.of(YEAR, MONTH, DAY, 7, 59), AppointmentLengthDTO.SIXTY_MINUTES, AppointmentStatusDTO.SCHEDULED, "");

            given(modelMapper.map(any(AppointmentCreationDTO.class), eq(Appointment.class))).willReturn(appointmentBefore);
            given(timeUtils.getNow()).willReturn(LocalDate.of(YEAR, MONTH, DAY - 1));

            MockHttpServletResponse response = mockMvc.perform(
                    post("/appointments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(creationString)
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
            assertThat(response.getErrorMessage()).contains("The visit must take place between 8:00 and 20:00");
        }

        @Test
        public void throwsErrorWhenAfterClinicWorkingHours() throws Exception {
            Appointment appointmentAfter =
                    new Appointment(1L, 22L, LocalDateTime.of(YEAR, MONTH, DAY, 20, 1), AppointmentLength.SIXTY_MINUTES, AppointmentStatus.SCHEDULED, "");

            given(modelMapper.map(any(AppointmentCreationDTO.class), eq(Appointment.class))).willReturn(appointmentAfter);
            given(timeUtils.getNow()).willReturn(LocalDate.of(YEAR, MONTH, DAY - 1));

            MockHttpServletResponse response = mockMvc.perform(
                    post("/appointments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(creationString)
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
            assertThat(response.getErrorMessage()).contains("The visit must take place between 8:00 and 20:00");
        }

        @Test
        public void throwsErrorWhenCollidingOtherAppointment() throws Exception {
            given(modelMapper.map(any(AppointmentCreationDTO.class), eq(Appointment.class))).willReturn(firstAppointment);
            given(timeUtils.getNow()).willReturn(LocalDate.of(YEAR, MONTH, DAY - 1));
            given(appointmentService.getCollidingCount(any(LocalDateTime.class), any(LocalDateTime.class))).willReturn(1L);

            MockHttpServletResponse response = mockMvc.perform(
                    post("/appointments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(creationString)
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
            assertThat(response.getErrorMessage()).contains("Appointment time cannot overlap other ones");
        }


        @Test
        public void successfulAppointmentCreation() throws Exception {
            given(modelMapper.map(any(AppointmentCreationDTO.class), eq(Appointment.class))).willReturn(firstAppointment);
            given(timeUtils.getNow()).willReturn(LocalDate.of(YEAR, MONTH, DAY - 1));
            given(appointmentService.getCollidingCount(any(LocalDateTime.class), any(LocalDateTime.class))).willReturn(0L);
            given(appointmentService.save(any(Appointment.class))).will(obj -> obj.getArgument(0));
            given(modelMapper.map(any(Appointment.class), eq(AppointmentResponseDTO.class))).willReturn(firstAppointmentDTO);

            MockHttpServletResponse response = mockMvc.perform(
                    post("/appointments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(creationString)
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(response.getContentAsString()).contains("1", "22", "scheduled");
        }
    }
}
