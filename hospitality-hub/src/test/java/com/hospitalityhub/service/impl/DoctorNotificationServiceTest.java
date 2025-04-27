package com.hospitalityhub.service.impl;

import com.hospitalityhub.entity.DoctorNotification;
import com.hospitalityhub.repository.DoctorNotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DoctorNotificationServiceTest {

    @Mock
    private DoctorNotificationRepository notificationRepository;

    @InjectMocks
    private DoctorNotificationService notificationService;

    @Captor
    private ArgumentCaptor<DoctorNotification> notificationCaptor;

    @BeforeEach
    void setUp() {
        notificationRepository = Mockito.mock(DoctorNotificationRepository.class);
        notificationService = new DoctorNotificationService();

        try {
            java.lang.reflect.Field field = DoctorNotificationService.class.getDeclaredField("notificationRepository");
            field.setAccessible(true);
            field.set(notificationService, notificationRepository);
        } catch (Exception e) {
            fail("Failed to set up test: " + e.getMessage());
        }

        notificationCaptor = ArgumentCaptor.forClass(DoctorNotification.class);
    }
    @Test
    void createAppointmentNotification_ShouldCreateAndSaveNotification() {
        // Arrange
        int doctorId = 123;
        String patientName = "John Doe";
        Long appointmentId = 456L;

        notificationService.createAppointmentNotification(doctorId, patientName, appointmentId);

        verify(notificationRepository, times(1)).save(notificationCaptor.capture());

        DoctorNotification capturedNotification = notificationCaptor.getValue();
        assertEquals((long) doctorId, capturedNotification.getDoctorId());
        assertEquals("New appointment request from patient " + patientName, capturedNotification.getMessage());
        assertEquals(false, capturedNotification.isRead());
        assertEquals("booking", capturedNotification.getAppointmentType());
        assertEquals(appointmentId, capturedNotification.getAppointmentId());

        assertNotNull(capturedNotification.getCreatedAt());
        assertTrue(capturedNotification.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(1)));
    }

    @Test
    void createAppointmentNotification_WithNullPatientName_ShouldHandleNull() {
        int doctorId = 123;
        String patientName = null;
        Long appointmentId = 456L;

        // Act
        notificationService.createAppointmentNotification(doctorId, patientName, appointmentId);

        // Assert
        verify(notificationRepository, times(1)).save(notificationCaptor.capture());

        DoctorNotification capturedNotification = notificationCaptor.getValue();
        assertEquals("New appointment request from patient null", capturedNotification.getMessage());
    }

    @Test
    void createAppointmentNotification_WithNullAppointmentId_ShouldHandleNull() {
        // Arrange
        int doctorId = 123;
        String patientName = "John Doe";
        Long appointmentId = null;

        // Act
        notificationService.createAppointmentNotification(doctorId, patientName, appointmentId);

        // Assert
        verify(notificationRepository, times(1)).save(notificationCaptor.capture());

        DoctorNotification capturedNotification = notificationCaptor.getValue();
        assertNull(capturedNotification.getAppointmentId());
    }
}