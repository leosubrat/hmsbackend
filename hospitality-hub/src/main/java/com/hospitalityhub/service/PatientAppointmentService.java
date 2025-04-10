package com.hospitalityhub.service;

import com.hospitalityhub.dto.PatientAppointmentDTO;
import com.hospitalityhub.entity.PatientAppointment;

import java.security.Principal;

public interface PatientAppointmentService {
    void savePatientAppointment(PatientAppointmentDTO patientAppointmentDTO, Principal principal);

}
