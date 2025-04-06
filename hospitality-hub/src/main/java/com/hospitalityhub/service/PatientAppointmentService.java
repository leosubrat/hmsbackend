package com.hospitalityhub.service;

import com.hospitalityhub.dto.PatientAppointmentDTO;
import com.hospitalityhub.entity.PatientAppointment;

public interface PatientAppointmentService {
    void savePatientAppointment(PatientAppointmentDTO patientAppointmentDTO);

}
