package com.hospitalityhub.controllers;


import com.hospitalityhub.dto.SignUpRequest;
import com.hospitalityhub.dto.SigninRequest;
import com.hospitalityhub.dto.UserDoctorDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hospitalityhub.shared.ApiURL.GET_ALL_USER;

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
    public ResponseEntity<?> getUserProfile(Principal principal) {
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

    @GetMapping(GET_ALL_USER)
    public ResponseEntity<?> getAllUsers() {
        List<UserDoctorDto> userDoctorDtoList = authenticationService.userList();
        Map<String, Object> response = new HashMap<>();
        response.put("data", userDoctorDtoList);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/update-user")
    public ResponseEntity<?> updateUser(@RequestParam Integer id, @RequestBody UserDoctorDto userDoctorDto) {
        try {
            authenticationService.updateUserDoctorByAdmin(id, userDoctorDto);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to update user");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/api/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam Integer id) {
        try {
            authenticationService.deleteUserDoctorByAdmin(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to delete user");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
