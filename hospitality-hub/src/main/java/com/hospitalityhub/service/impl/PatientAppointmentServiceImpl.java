package com.hospitalityhub.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalityhub.dto.PatientAppointmentDTO;
import com.hospitalityhub.entity.PatientAppointment;
import com.hospitalityhub.repository.PatientAppointmentRepository;
import com.hospitalityhub.service.PatientAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientAppointmentServiceImpl implements PatientAppointmentService {
    private final PatientAppointmentRepository patientAppointmentRepository;
    private final ObjectMapper objectMapper;
    public void saveUserAppointment(PatientAppointmentDTO patientAppointmentDTO){
        PatientAppointment patientAppointment = PatientAppointmentDTO.mapToPatientAppointment(patientAppointmentDTO);
        patientAppointmentRepository.save(patientAppointment);
    }

}
