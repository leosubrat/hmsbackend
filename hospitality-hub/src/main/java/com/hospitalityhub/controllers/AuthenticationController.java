package com.hospitalityhub.controllers;


import com.hospitalityhub.dto.SignUpRequest;
import com.hospitalityhub.dto.SigninRequest;
import com.hospitalityhub.service.AuthenticationService;
import com.hospitalityhub.shared.ApiResponse;
import com.hospitalityhub.shared.ApiURL;
import com.hospitalityhub.shared.JwtResponse;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping(ApiURL.USER_SIGN_UP)
  public ApiResponse userSignup(@RequestBody @Validated SignUpRequest request) {
    log.info("user signin{}",request);
    return authenticationService.signup(request);
  }

  @PostMapping(value = ApiURL.USER_SIGN_IN)
  public JwtResponse signin(@RequestBody SigninRequest request) {
    return authenticationService.signin(request);
  }


}
