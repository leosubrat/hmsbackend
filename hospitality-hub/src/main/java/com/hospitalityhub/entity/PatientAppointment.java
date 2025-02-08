package com.hospitalityhub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@Table(name = "patient-appointments")
@Getter
@Setter
public class PatientAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact", nullable = false, length = 10)
    private String contact;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

}
