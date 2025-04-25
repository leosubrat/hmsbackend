package com.hospitalityhub.controllers;

import com.hospitalityhub.dto.PatientAppointmentDTO;
import com.hospitalityhub.dto.UserDto;
import com.hospitalityhub.entity.PatientAppointment;
import com.hospitalityhub.repository.DoctorAvailabilityRepository;
import com.hospitalityhub.repository.PatientAppointmentRepository;
import com.hospitalityhub.service.impl.EmailService;
import com.hospitalityhub.service.PatientAppointmentService;
import com.hospitalityhub.shared.ApiURL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.security.Principal;
import java.util.*;

import static com.hospitalityhub.shared.ApiURL.*;

@RestController
@RequiredArgsConstructor
public class PatientAppointmentController {
    private final PatientAppointmentService patientAppointmentService;
    private final PatientAppointmentRepository patientAppointmentRepository;
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
   private final EmailService emailService;
    @PostMapping(SAVE_PATIENT_APPOINTMENT)
    public ResponseEntity<?> savePatientAppointment(@RequestBody PatientAppointmentDTO patientAppointmentDTO, Principal principal) {
        patientAppointmentService.savePatientAppointment(patientAppointmentDTO, principal);
        return ResponseEntity.ok().body(Map.of("message", "successfully saved"));
    }

    @GetMapping(GET_ALL_APPOINTMENT_PATIENT)
    public ResponseEntity<?> getUserAppointments(Principal principal) {
        String userEmail = principal.getName();
        List<PatientAppointmentDTO> userAppointments = patientAppointmentService.getAppointmentsByUserEmail(userEmail);
        if (userAppointments.isEmpty()) {
            return ResponseEntity.ok().body(Collections.emptyList());
        }

        return ResponseEntity.ok().body(userAppointments);
    }

    @GetMapping(ApiURL.GET_USER_DETAILS)
    public ResponseEntity<?> getUserDetails(Principal principal) {
        UserDto userDto = patientAppointmentService.getUserDetails(principal.getName());
        return ResponseEntity.ok().body(Objects.requireNonNullElse(userDto, Collections.emptyList()));
    }

    @GetMapping(ApiURL.PATIENT_DETAIL)
    public ResponseEntity<?> getAllPatientDetails(Principal principal) {
        return ResponseEntity.ok().body(patientAppointmentService.patientAppointmentDTOList(principal.getName()));
    }

    @PostMapping(ApiURL.APPOINTMENTS_APPROVED)
    public ResponseEntity<?> approvedByDoctor(@RequestParam Long appointmentId) {  
        PatientAppointment appointment = patientAppointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("Appointment not found with id: " + appointmentId));
        appointment.setStatus(true);
        patientAppointmentRepository.save(appointment);
        emailService.sendEmail(appointment.getPatientEmail(),"approved your request","Your request is apporoved by doctor");
        Map<String, String> response = new HashMap<>();
        response.put("message", "Appointment successfully approved by doctor");
        return ResponseEntity.ok(response);
    }
}