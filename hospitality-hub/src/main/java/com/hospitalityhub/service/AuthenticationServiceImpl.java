package com.hospitalityhub.service;


import com.hospitalityhub.config.JwtService;
import com.hospitalityhub.config.UserDetailService;
import com.hospitalityhub.dto.SignUpRequest;
import com.hospitalityhub.dto.SigninRequest;
import com.hospitalityhub.entity.Role;
import com.hospitalityhub.entity.User;
import com.hospitalityhub.exception.InvalidUserCredentialException;
import com.hospitalityhub.exception.UserAlreadyExistException;
import com.hospitalityhub.repository.UserRepository;
import com.hospitalityhub.shared.ApiResponse;
import com.hospitalityhub.shared.JwtResponse;
import com.hospitalityhub.shared.ResponseMessageConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.security.core.userdetails.UserDetails;
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

  /**
   * Registers a new user with the provided details.
   *
   * @param request SignUpRequest
   * @return ApiResponse
   * @throws UserAlreadyExistException if a user with the provided email already exists in the system.
   * @author Subrat Thapa
   */
  @Override
  public ApiResponse signup(SignUpRequest request) {
    Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
    if (userOptional.isPresent()) {
      throw new UserAlreadyExistException(ResponseMessageConstant.ALREADY_REGISTER);
    }
    var user = User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .password(passwordEncoders.encode(request.getPassword()))
            .role(Role.USER)
            .build();
    userRepository.save(user);
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



}
