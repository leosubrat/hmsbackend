package com.hospitalityhub.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DoctorDto {
    private int doctorId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Integer experience;
    private String expertise;
    private String salary;
    private String description;
    private byte[] photo;
    private String licenseNumber;
   private String specialization;
    // Add these fields for the custom date availability
    private String selectedDate;
    private List<Map<String, String>> timeSlots;

    // Keep this field if you need it for other purposes
    private DoctorAvailabilityDTO doctorAvailability;
}