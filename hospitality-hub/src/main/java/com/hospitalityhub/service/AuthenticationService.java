package com.hospitalityhub.service;


import com.hospitalityhub.dto.SignUpRequest;
import com.hospitalityhub.dto.SigninRequest;
import com.hospitalityhub.shared.ApiResponse;
import com.hospitalityhub.shared.JwtResponse;

public interface AuthenticationService {

  ApiResponse signup(SignUpRequest request);

  JwtResponse signin(SigninRequest request);



}