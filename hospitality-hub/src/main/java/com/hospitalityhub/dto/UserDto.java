package com.hospitalityhub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
