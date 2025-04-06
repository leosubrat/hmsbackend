package com.hospitalityhub.repository;

import com.hospitalityhub.entity.Doctor;
import com.hospitalityhub.entity.PatientAppointment;
import com.hospitalityhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientAppointmentRepository extends JpaRepository<PatientAppointment,Long> {

}
