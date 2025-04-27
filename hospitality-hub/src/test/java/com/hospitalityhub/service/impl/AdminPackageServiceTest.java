package com.hospitalityhub.service.impl;

import com.hospitalityhub.entity.AdminPackage;
import com.hospitalityhub.repository.AdminPackageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminPackageServiceTest {

    @Mock
    private AdminPackageRepository adminPackageRepository;

    @InjectMocks
    private AdminPackageService adminPackageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deletePackage_WhenPackageExists_ShouldDeletePackage() {
        // Arrange
        Integer packageId = 1;
        AdminPackage adminPackage = new AdminPackage();
        when(adminPackageRepository.findById(packageId)).thenReturn(Optional.of(adminPackage));

        // Act
        adminPackageService.deletePackage(packageId);

        // Assert
        verify(adminPackageRepository).findById(packageId);
        verify(adminPackageRepository).deleteById(packageId);
    }

    @Test
    void deletePackage_WhenPackageDoesNotExist_ShouldNotDeletePackage() {
        // Arrange
        Integer packageId = 1;
        when(adminPackageRepository.findById(packageId)).thenReturn(Optional.empty());

        // Act
        adminPackageService.deletePackage(packageId);

        // Assert
        verify(adminPackageRepository).findById(packageId);
        verify(adminPackageRepository, never()).deleteById(packageId);
    }
}
