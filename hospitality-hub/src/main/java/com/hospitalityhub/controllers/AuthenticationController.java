package com.hospitalityhub.controllers;


import com.hospitalityhub.dto.SignUpRequest;
import com.hospitalityhub.dto.SigninRequest;
import com.hospitalityhub.service.AuthenticationService;
import com.hospitalityhub.shared.ApiResponse;
import com.hospitalityhub.shared.ApiURL;
import com.hospitalityhub.shared.JwtResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor 
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping(ApiURL.USER_SIGN_UP)
  public ApiResponse userSignup(@RequestBody @Validated SignUpRequest request) {
    return authenticationService.signup(request);
  }

  @PostMapping(value = ApiURL.USER_SIGN_IN, produces = MediaType.APPLICATION_JSON_VALUE)
  public JwtResponse signin(@RequestBody SigninRequest request) {
    return authenticationService.signin(request);
  }


}
