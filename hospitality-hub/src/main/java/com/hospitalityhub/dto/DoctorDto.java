package com.hospitalityhub.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDto {
    private int doctorId;
    private String firstName;
    private String middleName;
    private String lastName;
    private int experience;
    private int age;
    private String expertise;
    private String salary;
}
