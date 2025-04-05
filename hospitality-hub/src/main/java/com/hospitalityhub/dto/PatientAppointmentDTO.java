package com.hospitalityhub.dto;

import com.hospitalityhub.entity.PatientAppointment;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@ToString
public class PatientAppointmentDTO {
    private Long id;
    private String name;
    private String contact;
    private String date;
    private String time;
    private String type;
    private String notes;
    private String status;
    private String reason;
    private Integer doctorId;
    private String doctorName;
    private String specialization;
    private LocalDateTime dateTime;
    public static PatientAppointment mapToPatientAppointment(PatientAppointmentDTO patientAppointmentDTO){
        PatientAppointment patientAppointment = new PatientAppointment();
        patientAppointment.setContact(patientAppointmentDTO.getContact());
        patientAppointment.setDate(patientAppointmentDTO.getDate());
        patientAppointment.setTime(patientAppointmentDTO.getTime());
        patientAppointment.setContact(patientAppointmentDTO.getContact());
        patientAppointment.setNotes(patientAppointmentDTO.getNotes());
        patientAppointment.setType(patientAppointmentDTO.getType());
        return patientAppointment;
    }

}
