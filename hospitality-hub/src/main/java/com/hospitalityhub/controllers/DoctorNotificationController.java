package com.hospitalityhub.controllers;

// src/main/java/com/example/controller/DoctorNotificationController.java


import com.hospitalityhub.dto.DoctorDto;
import com.hospitalityhub.dto.DoctorNotificationDTO;
import com.hospitalityhub.service.impl.DoctorNotificationService;
import com.hospitalityhub.service.impl.DoctorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctor/notifications")
@RequiredArgsConstructor
public class DoctorNotificationController {


    private final DoctorNotificationService notificationService;
    private final DoctorServiceImpl doctorService;


    @GetMapping("/{doctorId}")
    public ResponseEntity<List<DoctorNotificationDTO>> getDoctorNotifications(@PathVariable Long doctorId) {
        List<DoctorNotificationDTO> notifications = notificationService.getDoctorNotifications(doctorId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{doctorId}/count")
    public ResponseEntity<Map<String, Long>> getUnreadNotificationCount(@PathVariable Long doctorId) {
        Long count = notificationService.getUnreadNotificationCount(doctorId);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable Long notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.ok().body(Map.of("message", "Notification marked as read"));
    }

    @PutMapping("/{doctorId}/read-all")
    public ResponseEntity<?> markAllNotificationsAsRead(@PathVariable Long doctorId) {
        notificationService.markAllNotificationsAsRead(doctorId);
        return ResponseEntity.ok().body(Map.of("message", "All notifications marked as read"));
    }
    @GetMapping("/profile")
    public ResponseEntity<DoctorDto> getDoctorProfile(Principal principal) {
        String username = principal.getName();
        DoctorDto doctorDto = doctorService.findDoctorByUsername(username);
        return ResponseEntity.ok(doctorDto);
    }
}
