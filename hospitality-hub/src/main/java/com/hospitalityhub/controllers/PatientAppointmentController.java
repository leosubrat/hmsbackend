package com.hospitalityhub.controllers;

import com.hospitalityhub.dto.PatientAppointmentDTO;
import com.hospitalityhub.dto.UserDto;
import com.hospitalityhub.entity.User;
import com.hospitalityhub.service.PatientAppointmentService;
import com.hospitalityhub.shared.ApiURL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.hospitalityhub.shared.ApiURL.GET_ALL_PATIENT;
import static com.hospitalityhub.shared.ApiURL.SAVE_PATIENT_APPOINTMENT;

@RestController
@RequiredArgsConstructor
public class PatientAppointmentController {
    private final PatientAppointmentService patientAppointmentService;

    @PostMapping(SAVE_PATIENT_APPOINTMENT)
    public ResponseEntity<?> savePatientAppointment(@RequestBody PatientAppointmentDTO patientAppointmentDTO, Principal principal){
        patientAppointmentService.savePatientAppointment(patientAppointmentDTO,principal);
        return ResponseEntity.ok().body(Map.of("message", "successfully saved"));
    }
    @GetMapping(GET_ALL_PATIENT)
    public ResponseEntity<?> getUserAppointments(Principal principal) {
        String userEmail = principal.getName();
        List<PatientAppointmentDTO> userAppointments = patientAppointmentService.getAppointmentsByUserEmail(userEmail);
        if (userAppointments.isEmpty()) {
            return ResponseEntity.ok().body(Collections.emptyList());
        }

        return ResponseEntity.ok().body(userAppointments);
    }

    @GetMapping(ApiURL.GET_USER_DETAILS)
    public ResponseEntity<?> getUserDetails(Principal principal){
        String userEmail=principal.getName();
        UserDto userDto = patientAppointmentService.getUserDetails(principal.getName());
        return ResponseEntity.ok().body(Objects.requireNonNullElse(userDto, Collections.emptyList()));
    }

    @GetMapping(ApiURL.PATIENT_DETAIL)
    public ResponseEntity<?> getAllPatientDetails(Principal principal){
        return ResponseEntity.ok().body(patientAppointmentService.patientAppointmentDTOList(principal.getName()));
    }

}