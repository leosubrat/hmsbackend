package com.hospitalityhub.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalityhub.dto.DoctorDto;
import com.hospitalityhub.dto.PatientAppointmentDTO;
import com.hospitalityhub.entity.Doctor;
import com.hospitalityhub.entity.PatientAppointment;
import com.hospitalityhub.entity.User;
import com.hospitalityhub.repository.DoctorRepository;
import com.hospitalityhub.repository.PatientAppointmentRepository;
import com.hospitalityhub.service.PatientAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientAppointmentServiceImpl implements PatientAppointmentService{
private final ObjectMapper objectMapper;
private final PatientAppointmentRepository patientAppointmentRepository;
private final DoctorNotificationService notificationService;
private final DoctorServiceImpl doctorService;
private  final DoctorRepository doctorRepository;
    @Override
    public void savePatientAppointment(PatientAppointmentDTO patientAppointmentDTO, Principal principal) {
        if (patientAppointmentDTO==null){
            throw new NotFoundException("patientAppointment is not found");
        }
        String licenseNumber = patientAppointmentDTO.getLicenseNumber();
        Doctor byLicenseNumber = doctorRepository.findByLicenseNumber(licenseNumber);
        if (byLicenseNumber == null) {
            throw new NotFoundException("liscence number is not found");

        }
        PatientAppointment patientAppointment = objectMapper.convertValue(patientAppointmentDTO, PatientAppointment.class);
        PatientAppointment savedAppointment = patientAppointmentRepository.save(patientAppointment);
        notificationService.createAppointmentNotification(
                byLicenseNumber.getDoctorId(),
                patientAppointmentDTO.getPatientName(),
                savedAppointment.getId()
        );

    }
}