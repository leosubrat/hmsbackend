package com.hospitalityhub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordDto {
    private String code;
    private String newPassword;
    private String confirmPassword;

    public ForgetPasswordDto(String code){
        this.code=code;
    }
}
