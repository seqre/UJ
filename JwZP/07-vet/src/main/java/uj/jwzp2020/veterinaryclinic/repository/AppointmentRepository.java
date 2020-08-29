package uj.jwzp2020.veterinaryclinic.repository;

import org.springframework.stereotype.Repository;
import uj.jwzp2020.veterinaryclinic.model.appointment.Appointment;

@Repository
public interface AppointmentRepository extends GeneralRepository<Appointment> {
}
