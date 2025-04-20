package com.hospitalityhub.controllers;

import com.hospitalityhub.dto.DoctorDto;
import com.hospitalityhub.service.impl.DoctorServiceImpl;
import com.hospitalityhub.shared.ApiURL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import static com.hospitalityhub.shared.ApiURL.*;

@RestController
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorServiceImpl doctorService;

    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(ApiURL.DOCTOR_LIST)
    public ResponseEntity<List<DoctorDto>> findAllDoctor() {
        return ResponseEntity.ok(doctorService.findAllDoctor());
    }

    @PutMapping(DOCTOR_UPDATE)
    public ResponseEntity<DoctorDto> updateDoctorProfile(@RequestBody DoctorDto doctorDto, Principal principal) {
        String username = principal.getName();
        DoctorDto updatedDoctor = doctorService.updateDoctorProfile(username,doctorDto);
        return ResponseEntity.ok(updatedDoctor);
    }

//    @PostMapping(ApiURL.APPROVED)
//    public ResponseEntity<?> approvedByDoctor(@RequestParam boolean status){
//        if (status==true){
//
//        }
//    }
}