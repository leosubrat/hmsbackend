package com.hospitalityhub.entity;

// src/main/java/com/example/model/DoctorNotification.java

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "doctor_notifications")
@Getter
@Setter
public class DoctorNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long doctorId;

    @Column(nullable = false, length = 255)
    private String message;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean isRead;

    @Column(length = 255)
    private String appointmentType;

    @Column
    private Long appointmentId;

}