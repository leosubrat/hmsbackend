package com.hospitalityhub.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class DoctorAvailabilityDTO {

    private Long id;

    private int doctorId;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;
}
