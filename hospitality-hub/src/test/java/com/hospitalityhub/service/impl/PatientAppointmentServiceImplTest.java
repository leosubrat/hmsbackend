package com.hospitalityhub.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalityhub.dto.PatientAppointmentDTO;
import com.hospitalityhub.entity.Doctor;
import com.hospitalityhub.entity.PatientAppointment;
import com.hospitalityhub.repository.DoctorAvailabilityRepository;
import com.hospitalityhub.repository.DoctorRepository;
import com.hospitalityhub.repository.PatientAppointmentRepository;
import com.hospitalityhub.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.webjars.NotFoundException;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PatientAppointmentServiceImplTest {

    @Mock
    private PatientAppointmentRepository patientAppointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private DoctorNotificationService notificationService;

    @Mock
    private DoctorServiceImpl doctorService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DoctorAvailabilityRepository doctorAvailabilityRepository;

    @Mock
    private Principal principal;

    private PatientAppointmentServiceImpl patientAppointmentService;

    private PatientAppointmentDTO patientAppointmentDTO;
    private Doctor doctor;
    private PatientAppointment patientAppointment;

    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);

        // Creating constructor-based dependency injection with all required dependencies
        patientAppointmentService = new PatientAppointmentServiceImpl(
                objectMapper,
                patientAppointmentRepository,
                notificationService,
                doctorService,
                doctorRepository,
                userRepository,
                doctorAvailabilityRepository
        );

        patientAppointmentDTO = new PatientAppointmentDTO();
        patientAppointmentDTO.setLicenseNumber("MED12345");
        patientAppointmentDTO.setPatientName("John Doe");
        patientAppointmentDTO.setAppointmentDate("2025-05-15");
        patientAppointmentDTO.setAppointmentTime("10:00 AM");
        patientAppointmentDTO.setDescription("Regular checkup");

        doctor = new Doctor();
        doctor.setDoctorId(123);
        doctor.setLicenseNumber("MED12345");

        patientAppointment = new PatientAppointment();
        patientAppointment.setId(1L);
        patientAppointment.setPatientName("John Doe");
        patientAppointment.setAppointmentDate("2025-05-15");
        patientAppointment.setAppointmentTime("10:00 AM");
        patientAppointment.setDescription("Regular checkup");
        patientAppointment.setStatus(false);
    }

    @Test
    void savePatientAppointment() {
        when(doctorRepository.findByLicenseNumber(patientAppointmentDTO.getLicenseNumber())).thenReturn(doctor);
        when(objectMapper.convertValue(patientAppointmentDTO, PatientAppointment.class)).thenReturn(patientAppointment);
        when(patientAppointmentRepository.save(any(PatientAppointment.class))).thenReturn(patientAppointment);
        patientAppointmentService.savePatientAppointment(patientAppointmentDTO, principal);
        verify(patientAppointmentRepository).save(patientAppointment);
        verify(notificationService).createAppointmentNotification(
                doctor.getDoctorId(),
                patientAppointmentDTO.getPatientName(),
                patientAppointment.getId()
        );
        assertFalse(patientAppointment.isStatus());
    }

    @Test
    void savePatientAppointment_NullDTO_ThrowsNotFoundException() {
        assertThrows(NotFoundException.class, () -> {
            patientAppointmentService.savePatientAppointment(null, principal);
        });
        verifyNoInteractions(patientAppointmentRepository, notificationService);
    }

    @Test
    void savePatientAppointment_DoctorNotFound_ThrowsNotFoundException() {
        when(doctorRepository.findByLicenseNumber(patientAppointmentDTO.getLicenseNumber())).thenReturn(null);

       assertThrows(NotFoundException.class, () -> {
            patientAppointmentService.savePatientAppointment(patientAppointmentDTO, principal);
        });

        verify(doctorRepository).findByLicenseNumber(patientAppointmentDTO.getLicenseNumber());
        verifyNoInteractions(patientAppointmentRepository, notificationService);
    }
}