package com.hospitalityhub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Entity
@Table(name = "patient-appointments")
@Getter
@Setter
public class PatientAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    private LocalDateTime dateTime;

    private String status; // SCHEDULED, COMPLETED, CANCELED

    private String reason;
    private String name;
    private String contact;
    private String date;
    private String time;
    private String type;
    private String notes;
}