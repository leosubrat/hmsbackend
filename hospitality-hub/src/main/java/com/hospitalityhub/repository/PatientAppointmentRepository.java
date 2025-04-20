package com.hospitalityhub.repository;

import com.hospitalityhub.entity.PatientAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientAppointmentRepository extends JpaRepository<PatientAppointment,Long> {

    List<PatientAppointment> findByPatientEmail(String email);

    PatientAppointment findByStatus(boolean status);
}
