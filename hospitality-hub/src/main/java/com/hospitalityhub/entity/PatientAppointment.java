package com.hospitalityhub.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "patient_appointments")
@Getter
@Setter
public class PatientAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "patient_email")
    private String patientEmail;

    @Column(name = "patient_phone")
    private String patientPhone;

    @Column(name = "appointment_date")
    private String appointmentDate;

    @Column(name = "appointment_time")
    private String appointmentTime;

    @Column(name = "reason_for_visit")
    private String reasonForVisit;

    @Column(name = "insurance")
    private String insurance;

    @Column(name = "is_new_patient")
    private boolean isNewPatient;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "doctor_specialty")
    private String doctorSpecialty;

    @Column(name="status")
    private boolean status;
}