package com.hospitalityhub.controllers;

import com.hospitalityhub.dto.AdminPackageDTO;
import com.hospitalityhub.entity.AdminPackage;
import com.hospitalityhub.service.impl.AdminPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.hospitalityhub.shared.ApiURL.*;

@RestController
public class AdminPackageController {

    @Autowired
    private AdminPackageService adminPackageService;

    @PostMapping(CREATE_WITH_TEST)
    public ResponseEntity<Map<String, String>> createPackage(@RequestBody AdminPackageDTO packageDTO) {
        adminPackageService.createPackageByAdmin(packageDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Package created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(GET_ALL_PACKAGE)
    public ResponseEntity<List<AdminPackageDTO>> getAllPackages() {
        List<AdminPackageDTO> packages = adminPackageService.adminPackageDTOList();
        return new ResponseEntity<>(packages, HttpStatus.OK);
    }

    @DeleteMapping(DELETE_PACKAGE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletePackage(@RequestParam Integer packageId) {
        adminPackageService.deletePackage(packageId);
        return ResponseEntity.noContent().build();
    }
}