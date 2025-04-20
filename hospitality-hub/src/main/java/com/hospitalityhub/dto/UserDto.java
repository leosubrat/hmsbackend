package com.hospitalityhub.dto;

import com.hospitalityhub.entity.DoctorAvailability;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
    private String forgetPasswordCode;
    private String phone;
    private List<DoctorAvailability> doctorAvailabilities;
}
