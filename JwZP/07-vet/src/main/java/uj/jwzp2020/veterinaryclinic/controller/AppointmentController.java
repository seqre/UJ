package uj.jwzp2020.veterinaryclinic.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uj.jwzp2020.veterinaryclinic.model.appointment.Appointment;
import uj.jwzp2020.veterinaryclinic.model.appointment.dto.AppointmentChangeDataDTO;
import uj.jwzp2020.veterinaryclinic.model.appointment.dto.AppointmentCreationDTO;
import uj.jwzp2020.veterinaryclinic.model.appointment.dto.AppointmentResponseDTO;
import uj.jwzp2020.veterinaryclinic.service.AppointmentService;
import uj.jwzp2020.veterinaryclinic.utils.TimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final static LocalTime START = LocalTime.of(8, 0);
    private final static LocalTime END = LocalTime.of(20, 0);

    private final AppointmentService appointmentService;
    private final ModelMapper modelMapper;
    private final TimeUtils timeUtils;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, ModelMapper modelMapper, TimeUtils timeUtils) {
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
        this.timeUtils = timeUtils;
    }

    @GetMapping
    @ResponseBody
    public List<AppointmentResponseDTO> getAppointments(@RequestParam(required = false, defaultValue = "") String day) {
        log.info("Got request for all appointments" + (!day.equals("") ? " for day " + day : ""));
        List<Appointment> appointments = appointmentService.getAppointments();

        if (!day.equals("")) {
            LocalDate date;
            try {
                date = LocalDate.parse(day);
            } catch (DateTimeParseException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided date is not in format: yyyy-mm-dd");
            }
            appointments = appointments.stream()
                    .filter(app -> app.getDate().toLocalDate().isEqual(date))
                    .collect(Collectors.toList());
        }

        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public AppointmentResponseDTO getAppointmentById(@PathVariable("id") Long id) {
        log.info("Got request for appointment with id " + id);
        Appointment appointment = appointmentService.getAppointmentById(id);
        return modelMapper.map(appointment, AppointmentResponseDTO.class);
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public AppointmentResponseDTO changeAppointmentDataById(@PathVariable("id") Long id, @RequestBody AppointmentChangeDataDTO dto) {
        log.info("Got request for changing appointment data with id " + id);
        Appointment appointment = appointmentService.getAppointmentById(id);

        appointmentService.changeData(appointment, dto);

        appointment = appointmentService.save(appointment);
        return modelMapper.map(appointment, AppointmentResponseDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public AppointmentResponseDTO createAppointment(@RequestBody AppointmentCreationDTO dto) {
        log.info("Got request for creating new appointment");
        Appointment appointment = modelMapper.map(dto, Appointment.class);

        LocalDateTime start = appointment.getDate();
        LocalDateTime end = appointment.getDate().plusMinutes(appointment.getDuration().getMinutes());

        if (!start.toLocalDate().isAfter(timeUtils.getNow())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Visits may be arranged from the following day onwards");
        }

        if (start.toLocalTime().isBefore(START) || start.toLocalTime().plusMinutes(appointment.getDuration().getMinutes()).isAfter(END)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The visit must take place between 8:00 and 20:00");
        }

        long colliding = appointmentService.getCollidingCount(start, end);
        if (colliding > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Appointment time cannot overlap other ones");
        }

        appointment = appointmentService.save(appointment);
        return modelMapper.map(appointment, AppointmentResponseDTO.class);
    }
}
