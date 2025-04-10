package com.hospitalityhub.repository;

import com.hospitalityhub.entity.PatientAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientAppointmentRepository extends JpaRepository<PatientAppointment,Long> {

}
