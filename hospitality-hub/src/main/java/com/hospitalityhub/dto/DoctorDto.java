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
    // Lists of time slots for today and tomorrow
    // Each time slot is a map with "startTime" and "endTime" keys
    private List<Map<String, String>> todayAvailability;
    private List<Map<String, String>> tomorrowAvailability;
}
