package com.hospitalityhub.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDoctorDto {
    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String role;
    private String email;
    private String phone;
    private Integer experience;
    private String expertise;
    private String salary;
    private String description;
    private String specialization;
    private String liscenceNumber;

}
