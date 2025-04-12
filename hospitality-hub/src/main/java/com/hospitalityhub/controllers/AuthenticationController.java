package com.hospitalityhub.controllers;


import com.hospitalityhub.dto.SignUpRequest;
import com.hospitalityhub.dto.SigninRequest;
import com.hospitalityhub.dto.UserDto;
import com.hospitalityhub.service.AuthenticationService;
import com.hospitalityhub.shared.ApiResponse;
import com.hospitalityhub.shared.ApiURL;
import com.hospitalityhub.shared.JwtResponse;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(ApiURL.USER_SIGN_UP)
    public ApiResponse userSignup(@RequestBody @Validated SignUpRequest request) {
        log.info("user signin{}", request);
        return authenticationService.signup(request);
    }

    @PostMapping(value = ApiURL.USER_SIGN_IN)
    public JwtResponse signin(@RequestBody SigninRequest request) {
        return authenticationService.signin(request);
    }

    @GetMapping(value = ApiURL.USER_PROFILE)
    public ResponseEntity<?> getUserProfile(Principal principal){
        return ResponseEntity.ok().body(authenticationService.getUserProfile(principal.getName()));
    }

    @PostMapping(value = ApiURL.PATIENT_UPDATE)
    public ResponseEntity<?> updatePatientProfile(@RequestBody UserDto userDto, Principal principal) {
        String userEmail = principal.getName();

        if (!userEmail.equals(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only update your own profile");
        }

        authenticationService.updateUserDetails(userDto);
        return ResponseEntity.ok().body(userDto); // Return the updated user object
    }
}
