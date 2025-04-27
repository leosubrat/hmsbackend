package com.hospitalityhub.service.impl;

import com.hospitalityhub.dto.SignUpRequest;
import com.hospitalityhub.entity.Doctor;
import com.hospitalityhub.entity.Role;
import com.hospitalityhub.entity.User;
import com.hospitalityhub.exception.UserAlreadyExistException;
import com.hospitalityhub.repository.DoctorRepository;
import com.hospitalityhub.repository.UserRepository;
import com.hospitalityhub.shared.ApiResponse;
import com.hospitalityhub.shared.ResponseMessageConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PasswordEncoder passwordEncoders;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private SignUpRequest regularUserRequest;
    private SignUpRequest doctorRequest;
    private User savedUser;

    @BeforeEach
    void setUp() {
        // Setup regular user request
        regularUserRequest = new SignUpRequest();
        regularUserRequest.setFirstName("John");
        regularUserRequest.setLastName("Doe");
        regularUserRequest.setEmail("john.doe@example.com");
        regularUserRequest.setPhone("1234567890");
        regularUserRequest.setPassword("password123");
        regularUserRequest.setRole("USER");

        // Setup doctor request
        doctorRequest = new SignUpRequest();
        doctorRequest.setFirstName("Jane");
        doctorRequest.setLastName("Smith");
        doctorRequest.setEmail("jane.smith@example.com");
        doctorRequest.setPhone("0987654321");
        doctorRequest.setPassword("password456");
        doctorRequest.setRole("DOCTOR");
        doctorRequest.setSpecialization("Cardiology");
        doctorRequest.setLicenseNumber("MED12345");
        doctorRequest.setYearsOfExperience(10);

        // Setup saved user
        savedUser = User.builder()
                .userId(1) // Assuming there's an ID field
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .password("encodedPassword")
                .role(Role.USER)
                .build();
    }

    @Test
    void signup() {
        // Implement the main test case here
        // Using the regular user test for this empty method
        when(userRepository.findByEmail(regularUserRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoders.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        ApiResponse response = authenticationService.signup(regularUserRequest);

        assertEquals(ResponseMessageConstant.SUCCESSFULLY_SAVE, response.getMessage());
        verify(userRepository, times(1)).findByEmail(regularUserRequest.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void signup_RegisterRegularUser_Success() {
        // Arrange
        when(userRepository.findByEmail(regularUserRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoders.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        ApiResponse response = authenticationService.signup(regularUserRequest);

        // Assert
        assertEquals(ResponseMessageConstant.SUCCESSFULLY_SAVE, response.getMessage());
        verify(userRepository, times(1)).findByEmail(regularUserRequest.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
        verify(doctorRepository, never()).save(any(Doctor.class));
    }

    @Test
    void signup_RegisterDoctor_Success() {
        // Arrange
        User savedDoctor = User.builder()
                .userId(2)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .phone("0987654321")
                .password("encodedPassword")
                .role(Role.DOCTOR)
                .build();

        when(userRepository.findByEmail(doctorRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoders.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedDoctor);
        when(doctorRepository.save(any(Doctor.class))).thenReturn(new Doctor());

        // Act
        ApiResponse response = authenticationService.signup(doctorRequest);

        // Assert
        assertEquals(ResponseMessageConstant.SUCCESSFULLY_SAVE, response.getMessage());
        verify(userRepository, times(1)).findByEmail(doctorRequest.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    void signup_UserAlreadyExists_ThrowsException() {
        // Arrange
        when(userRepository.findByEmail(regularUserRequest.getEmail())).thenReturn(Optional.of(savedUser));

        // Act & Assert
        assertThrows(UserAlreadyExistException.class, () -> {
            authenticationService.signup(regularUserRequest);
        });

        verify(userRepository, times(1)).findByEmail(regularUserRequest.getEmail());
        verify(userRepository, never()).save(any(User.class));
        verify(doctorRepository, never()).save(any(Doctor.class));
    }
    @Test
    void signup_InvalidRole_SavesUserWithNullRole() {
        // Arrange
        SignUpRequest invalidRoleRequest = new SignUpRequest();
        invalidRoleRequest.setFirstName("John");
        invalidRoleRequest.setLastName("Doe");
        invalidRoleRequest.setEmail("john.doe@example.com");
        invalidRoleRequest.setPhone("1234567890");
        invalidRoleRequest.setPassword("password123");
        invalidRoleRequest.setRole("ADMIN");

        when(userRepository.findByEmail(invalidRoleRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoders.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        ApiResponse response = authenticationService.signup(invalidRoleRequest);

        assertEquals(ResponseMessageConstant.SUCCESSFULLY_SAVE, response.getMessage());
        verify(userRepository, times(1)).save(any(User.class));
        verify(doctorRepository, never()).save(any(Doctor.class));
    }
}