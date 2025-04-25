package com.hospitalityhub.controllers;

import com.hospitalityhub.dto.DoctorDto;
import com.hospitalityhub.entity.PatientAppointment;
import com.hospitalityhub.repository.PatientAppointmentRepository;
import com.hospitalityhub.service.impl.DoctorServiceImpl;
import com.hospitalityhub.shared.ApiURL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hospitalityhub.shared.ApiURL.DOCTOR_UPDATE;

@RestController
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorServiceImpl doctorService;
    private final PatientAppointmentRepository patientAppointmentRepository;
    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(ApiURL.DOCTOR_LIST)
    public ResponseEntity<List<DoctorDto>> findAllDoctor() {
        return ResponseEntity.ok(doctorService.findAllDoctor());
    }

    @PutMapping(DOCTOR_UPDATE)
    public ResponseEntity<?> updateDoctorProfile(@RequestBody DoctorDto doctorDto, Principal principal) {
        String username = principal.getName();
        DoctorDto updatedDoctor = doctorService.updateDoctorProfile(username, doctorDto);

        Map<String, Object> response = new HashMap<>();
        response.put("data", updatedDoctor);
        response.put("message", "Profile updated successfully");
        response.put("status", true);

        return ResponseEntity.ok(response);
    }

}