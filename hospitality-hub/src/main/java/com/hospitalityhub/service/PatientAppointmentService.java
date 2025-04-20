package com.hospitalityhub.service;

import com.hospitalityhub.dto.PatientAppointmentDTO;
import com.hospitalityhub.dto.UserDto;
import com.hospitalityhub.entity.PatientAppointment;

import java.security.Principal;
import java.util.List;

public interface PatientAppointmentService {
    void savePatientAppointment(PatientAppointmentDTO patientAppointmentDTO, Principal principal);

    List<PatientAppointmentDTO> getAppointmentsByUserEmail(String email);

     UserDto getUserDetails(String email);

    public List<PatientAppointment> patientAppointmentDTOList(String doctorEmail);

}
