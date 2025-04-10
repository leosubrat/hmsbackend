package com.hospitalityhub.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class DoctorNotificationDTO {
    private Long id;
    private Long doctorId;
    private String message;
    private LocalDateTime createdAt;
    private boolean isRead;
    private String appointmentType;
    private Long appointmentId;
}
