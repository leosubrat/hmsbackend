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
import java.util.List;

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
    public ResponseEntity<DoctorDto> updateDoctorProfile(@RequestBody DoctorDto doctorDto, Principal principal) {
        String username = principal.getName();
        DoctorDto updatedDoctor = doctorService.updateDoctorProfile(username, doctorDto);
        return ResponseEntity.ok(updatedDoctor);
    }

    @PostMapping(ApiURL.APPOINTMENTS_APPROVED)
    public ResponseEntity<?> approvedByDoctor(@RequestParam boolean status) {
        PatientAppointment byStatus = patientAppointmentRepository.findByStatus(status);
        System.out.println(byStatus);
        try{
            if (byStatus.isStatus()){
                byStatus.setStatus(true);
            }
        }catch (NullPointerException exception){
            throw new NullPointerException("status is not found");
        }

        return ResponseEntity.ok().body("Approved by doctor");
    }
}