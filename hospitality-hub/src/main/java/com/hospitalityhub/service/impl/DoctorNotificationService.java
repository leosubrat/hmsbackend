package com.hospitalityhub.service.impl;

// src/main/java/com/example/service/DoctorNotificationService.java


import com.hospitalityhub.dto.DoctorNotificationDTO;
import com.hospitalityhub.entity.DoctorNotification;
import com.hospitalityhub.repository.DoctorNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorNotificationService {

    @Autowired
    private DoctorNotificationRepository notificationRepository;

    public void createAppointmentNotification(int doctorId, String patientName, Long appointmentId) {
        DoctorNotification notification = new DoctorNotification();
        notification.setDoctorId((long) doctorId);
        notification.setMessage("New appointment request from patient " + patientName);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);
        notification.setAppointmentType("booking");
        notification.setAppointmentId(appointmentId);

        notificationRepository.save(notification);
    }

    public List<DoctorNotificationDTO> getDoctorNotifications(Long doctorId) {
        List<DoctorNotification> notifications = notificationRepository.findByDoctorIdOrderByCreatedAtDesc(doctorId);
        return notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Long getUnreadNotificationCount(Long doctorId) {
        return notificationRepository.countUnreadNotifications(doctorId);
    }

    public void markNotificationAsRead(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }

    public void markAllNotificationsAsRead(Long doctorId) {
        List<DoctorNotification> notifications = notificationRepository.findByDoctorIdOrderByCreatedAtDesc(doctorId);
        notifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(notifications);
    }

    private DoctorNotificationDTO convertToDTO(DoctorNotification notification) {
        DoctorNotificationDTO dto = new DoctorNotificationDTO();
        dto.setId(notification.getId());
        dto.setDoctorId(notification.getDoctorId());
        dto.setMessage(notification.getMessage());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setRead(notification.isRead());
        dto.setAppointmentType(notification.getAppointmentType());
        dto.setAppointmentId(notification.getAppointmentId());
        return dto;
    }
}