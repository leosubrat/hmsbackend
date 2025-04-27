package com.hospitalityhub.controllers;

import com.hospitalityhub.dto.DoctorNotificationDTO;
import com.hospitalityhub.service.impl.DoctorNotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorNotificationControllerTest {

    @Mock
    private DoctorNotificationService notificationService;

    @InjectMocks
    private DoctorNotificationController notificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDoctorNotifications_ShouldReturnNotifications() {
        Long doctorId = 1L;
        List<DoctorNotificationDTO> expectedNotifications = new ArrayList<>();
        DoctorNotificationDTO notification1 = new DoctorNotificationDTO();
        notification1.setId(1L);
        notification1.setMessage("Test message 1");

        DoctorNotificationDTO notification2 = new DoctorNotificationDTO();
        notification2.setId(2L);
        notification2.setMessage("Test message 2");
        expectedNotifications.add(notification1);
        expectedNotifications.add(notification2);
        when(notificationService.getDoctorNotifications(doctorId)).thenReturn(expectedNotifications);
        ResponseEntity<List<DoctorNotificationDTO>> response = notificationController.getDoctorNotifications(doctorId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedNotifications, response.getBody());
        assertEquals(2, response.getBody().size());
        verify(notificationService, times(1)).getDoctorNotifications(doctorId);
    }

    @Test
    void getDoctorNotifications_WhenEmptyList_ShouldReturnEmptyList() {
        Long doctorId = 1L;
        List<DoctorNotificationDTO> emptyList = new ArrayList<>();
        when(notificationService.getDoctorNotifications(doctorId)).thenReturn(emptyList);
        ResponseEntity<List<DoctorNotificationDTO>> response = notificationController.getDoctorNotifications(doctorId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(notificationService).getDoctorNotifications(doctorId);
    }

    @Test
    void getDoctorNotifications_WhenServiceThrowsException_ShouldPropagateException() {
        Long doctorId = 1L;
        when(notificationService.getDoctorNotifications(doctorId))
                .thenThrow(new RuntimeException("Error retrieving notifications"));

        assertThrows(RuntimeException.class, () -> {
            notificationController.getDoctorNotifications(doctorId);
        });
        verify(notificationService).getDoctorNotifications(doctorId);
    }
}

