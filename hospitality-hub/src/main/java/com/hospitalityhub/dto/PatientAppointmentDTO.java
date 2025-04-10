package com.hospitalityhub.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientAppointmentDTO {

    private String patientName;
    private String patientEmail;
    private String patientPhone;
    private String appointmentDate; // String format for easier JSON serialization/deserialization
    private String appointmentTime;
    private String reasonForVisit;
    private String insurance;
    private boolean isNewPatient;
    private Long doctorId;
    private String doctorName;
    private String doctorSpecialty;
    private String licenseNumber;

    // Default constructor
    public PatientAppointmentDTO() {
    }

    public PatientAppointmentDTO(String patientName, String patientEmail, String patientPhone,
                                 String appointmentDate, String appointmentTime, String reasonForVisit,
                                 String insurance, boolean isNewPatient, Long doctorId,
                                 String doctorName, String doctorSpecialty) {
        this.patientName = patientName;
        this.patientEmail = patientEmail;
        this.patientPhone = patientPhone;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.reasonForVisit = reasonForVisit;
        this.insurance = insurance;
        this.isNewPatient = isNewPatient;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorSpecialty = doctorSpecialty;
    }

}