package com.hospitalityhub.service;


import com.hospitalityhub.dto.SignUpRequest;
import com.hospitalityhub.dto.SigninRequest;
import com.hospitalityhub.dto.UserDoctorDto;
import com.hospitalityhub.dto.UserDto;
import com.hospitalityhub.entity.User;
import com.hospitalityhub.shared.ApiResponse;
import com.hospitalityhub.shared.JwtResponse;

import java.util.List;

public interface AuthenticationService {

    ApiResponse signup(SignUpRequest request);

    JwtResponse signin(SigninRequest request);

    UserDto getUserProfile(String userEmail);

     void updateUserDetails(UserDto userDto);

     List<UserDoctorDto> userList();

     void updateUserDoctorByAdmin(Integer id, UserDoctorDto userDoctorDto);

     void deleteUserDoctorByAdmin(Integer id);
    }