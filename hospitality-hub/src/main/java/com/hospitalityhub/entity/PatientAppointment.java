package com.hospitalityhub.entity;


import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "patient_appointments")
public class PatientAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @Column(name = "patient_email", nullable = false)
    private String patientEmail;

    @Column(name = "patient_phone", nullable = false)
    private String patientPhone;

    @Column(name = "appointment_date")
    private String appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    private String appointmentTime;

    @Column(name = "reason_for_visit", nullable = false, columnDefinition = "TEXT")
    private String reasonForVisit;

    @Column(name = "insurance")
    private String insurance;

    @Column(name = "is_new_patient")
    private boolean isNewPatient;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "doctor_name", nullable = false)
    private String doctorName;

    @Column(name = "doctor_specialty", nullable = false)
    private String doctorSpecialty;

}