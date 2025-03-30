package com.hospitalityhub.controllers;

import com.hospitalityhub.dto.DoctorDto;
import com.hospitalityhub.service.impl.DoctorServiceImpl;
import com.hospitalityhub.shared.ApiURL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorServiceImpl doctorService;
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(ApiURL.DOCTOR_LIST)
    public ResponseEntity<List<DoctorDto>> findAllDoctor(){
        return ResponseEntity.ok(doctorService.findAllDoctor());
    }
    @PutMapping("/update/doctor")
    public void updateDoctor(Authentication authentication,@RequestBody DoctorDto doctorDto){
        doctorService.updateDoctor(authentication,doctorDto);
    }
}
