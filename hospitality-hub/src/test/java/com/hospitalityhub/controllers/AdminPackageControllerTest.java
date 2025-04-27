package com.hospitalityhub.controllers;

import com.hospitalityhub.dto.AdminPackageDTO;
import com.hospitalityhub.service.impl.AdminPackageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminPackageControllerTest {


    @Mock
    private AdminPackageService adminPackageService;

    @InjectMocks
    private AdminPackageController adminPackageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPackage_ShouldReturnCreatedStatus() {
        // Arrange
        AdminPackageDTO packageDTO = new AdminPackageDTO();
        packageDTO.setPackageName("Test Package");
        packageDTO.setPackagePrice(100);
        // Set other necessary fields in the DTO

        doNothing().when(adminPackageService).createPackageByAdmin(packageDTO);

        // Act
        ResponseEntity<Map<String, String>> responseEntity = adminPackageController.createPackage(packageDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Package created successfully", responseEntity.getBody().get("message"));
        verify(adminPackageService, times(1)).createPackageByAdmin(packageDTO);
    }

    @Test
    void createPackage_ShouldCallServiceMethod() {
        // Arrange
        AdminPackageDTO packageDTO = new AdminPackageDTO();
        // Set necessary fields

        // Act
        adminPackageController.createPackage(packageDTO);

        // Assert
        verify(adminPackageService).createPackageByAdmin(packageDTO);
    }

    @Test
    void createPackage_WhenServiceThrowsException_ShouldPropagateException() {
        // Arrange
        AdminPackageDTO packageDTO = new AdminPackageDTO();
        doThrow(new RuntimeException("Error creating package")).when(adminPackageService).createPackageByAdmin(packageDTO);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            adminPackageController.createPackage(packageDTO);
        });
        verify(adminPackageService).createPackageByAdmin(packageDTO);
    }
}