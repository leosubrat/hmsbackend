package com.hospitalityhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {
    private Long id;
    private Integer doctorId;
    private LocalDateTime dateTime;
    private String status;
    private String reason;

    // Fields for UI display
    private String doctorName;
    private String specialization;
    private String date;
    private String time;
}