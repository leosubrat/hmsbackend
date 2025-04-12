package com.hospitalityhub.service;


import com.hospitalityhub.dto.SignUpRequest;
import com.hospitalityhub.dto.SigninRequest;
import com.hospitalityhub.dto.UserDto;
import com.hospitalityhub.entity.User;
import com.hospitalityhub.shared.ApiResponse;
import com.hospitalityhub.shared.JwtResponse;

public interface AuthenticationService {

    ApiResponse signup(SignUpRequest request);

    JwtResponse signin(SigninRequest request);

    UserDto getUserProfile(String userEmail);

    public void updateUserDetails(UserDto userDto);

}