package com.hospitalityhub.service.impl;


import com.hospitalityhub.config.JwtService;
import com.hospitalityhub.config.UserDetailService;
import com.hospitalityhub.dto.SignUpRequest;
import com.hospitalityhub.dto.SigninRequest;
import com.hospitalityhub.dto.UserDto;
import com.hospitalityhub.entity.Doctor;
import com.hospitalityhub.entity.Role;
import com.hospitalityhub.entity.User;
import com.hospitalityhub.exception.InvalidUserCredentialException;
import com.hospitalityhub.exception.UserAlreadyExistException;
import com.hospitalityhub.repository.DoctorRepository;
import com.hospitalityhub.repository.UserRepository;
import com.hospitalityhub.service.AuthenticationService;
import com.hospitalityhub.shared.ApiResponse;
import com.hospitalityhub.shared.JwtResponse;
import com.hospitalityhub.shared.ResponseMessageConstant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * This class is used to create the login and signup
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoders;
    private final JwtService jwtService;
    private final UserDetailService userService;
    private final DoctorRepository doctorRepository;

    /**
     * Registers a new user with the provided details.
     *
     * @param request SignUpRequest
     * @return ApiResponse
     * @throws UserAlreadyExistException if a user with the provided email already exists in the system.
     * @author Subrat Thapa
     */
    @Override
    @Transactional
    public ApiResponse signup(SignUpRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistException(ResponseMessageConstant.ALREADY_REGISTER);
        }

        Role role = null;
        if (request.getRole().equals("DOCTOR")) {
            role = Role.DOCTOR;
        } else if (request.getRole().equals("USER")) {
            role = Role.USER;
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoders.encode(request.getPassword()))
                .role(role)
                .build();
        User savedUser = userRepository.save(user);
        if (role == Role.DOCTOR) {
            Doctor doctor = Doctor.builder()
                    .user(savedUser)
                    .specialization(request.getSpecialization())
                    .licenseNumber(request.getLicenseNumber())
                    .yearsOfExperience(request.getYearsOfExperience())
                    .build();
            doctorRepository.save(doctor);
        }
        return new ApiResponse(ResponseMessageConstant.SUCCESSFULLY_SAVE);
    }


    @Override
    public JwtResponse signin(SigninRequest request) {
        UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
        if (!passwordEncoders.matches(request.getPassword(), userDetails.getPassword())) {
            throw new InvalidUserCredentialException(ResponseMessageConstant.INVALID_EMAIL_AND_PASSWORD_COMBINATION);
        }
        List<String> stringList = jwtService.generateToken(userDetails);
        return new JwtResponse(stringList.get(0), stringList.get(1), ResponseMessageConstant.SUCCESSFULLY_LOGIN);
    }

    @Override
    public UserDto getUserProfile(String userEmail) {
        UserDto userDto = new UserDto();
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("userEmail is not  found");
        }
        userDto.setEmail(userOptional.get().getEmail());
        userDto.setPhone(userOptional.get().getPhone());
        userDto.setFirstName(userOptional.get().getFirstName());
        userDto.setMiddleName(userOptional.get().getMiddleName());
        userDto.setLastName(userOptional.get().getLastName());
        return userDto;
    }

    public void updateUserDetails(UserDto userDto) {
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("user is not found");
        }

        User user = userOptional.get();
        user.setPhone(userDto.getPhone());
        user.setFirstName(userDto.getFirstName());
        user.setMiddleName(userDto.getMiddleName());
        user.setLastName(userDto.getLastName());

        userRepository.save(user);
    }

    public String forgetPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(passwordEncoders.encode(user.getPassword()));
            return "user is save succesfully";
        }
        return "user is not found";
    }


}
