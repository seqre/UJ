package uj.jwzp2020.veterinaryclinic.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uj.jwzp2020.veterinaryclinic.model.appointment.Appointment;
import uj.jwzp2020.veterinaryclinic.model.appointment.AppointmentStatus;
import uj.jwzp2020.veterinaryclinic.model.appointment.dto.AppointmentChangeDataDTO;
import uj.jwzp2020.veterinaryclinic.repository.AppointmentRepository;
import uj.jwzp2020.veterinaryclinic.repository.PetRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, PetRepository petRepository, ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
        this.modelMapper = modelMapper;
    }

    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Unknown appointment with id " + id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown appointment with id " + id);
                });
    }

    @Transactional
    public Appointment save(Appointment appointment) {
        petRepository.findById(appointment.getPetId())
                .orElseThrow(() -> {
                    log.warn("Unknown pet with id " + appointment.getPetId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown pet with id " + appointment.getPetId());
                });

        log.info("Saving new appointment: " + appointment.toString());
        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment changeData(Appointment appointment, AppointmentChangeDataDTO dto) {
        if (dto.getDescription() != null) {
            log.info("Changing description of appointment with id " + appointment.getId());
            appointment.setDescription(dto.getDescription());
        }
        if (dto.getStatus() != null) {
            log.info("Changing status of appointment with id " + appointment.getId());
            appointment.setStatus(modelMapper.map(dto.getStatus(), AppointmentStatus.class));
        }
        return appointment;
    }

    public long getCollidingCount(LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findAll()
                .stream()
                .filter(app -> app.getStatus().equals(AppointmentStatus.SCHEDULED))
                .filter(app -> app.getDate().plusMinutes(app.getDuration().getMinutes()).isAfter(start))
                .filter(app -> app.getDate().isBefore(end))
                .count();
    }
}
