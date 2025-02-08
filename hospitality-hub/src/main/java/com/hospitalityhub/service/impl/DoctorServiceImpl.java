package com.hospitalityhub.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalityhub.dao.GeneralDao;
import com.hospitalityhub.dto.DoctorDto;
import com.hospitalityhub.entity.Doctor;
import com.hospitalityhub.entity.User;
import com.hospitalityhub.exception.UserAlreadyExistException;
import com.hospitalityhub.repository.DoctorRepository;
import com.hospitalityhub.repository.UserRepository;
import com.hospitalityhub.shared.ResponseMessageConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl {
    private final GeneralDao generalDao;
    private final DoctorRepository doctorRepository;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public List<DoctorDto> findAllDoctor(Authentication authentication) {
        List<Doctor> doctorList = doctorRepository.findAll();
        List<DoctorDto> doctorDtoList = new ArrayList<>();
        for (Doctor doctor : doctorList) {
            DoctorDto doctorDto = objectMapper.convertValue(doctor, DoctorDto.class);
            doctorDtoList.add(doctorDto);
        }
        String principal = authentication.getName();
        return doctorDtoList;
    }

    public void updateDoctor(Authentication authentication,DoctorDto doctorDto) {
        String principal = authentication.getName();
        Optional<User> userFindByEmail = userRepository.findByEmail(principal);
        if (userFindByEmail.isPresent()) {
            throw new UserAlreadyExistException(ResponseMessageConstant.ALREADY_REGISTER);
        }
        User user = userFindByEmail.get();
        doctorDto.setFirstName(user.getFirstName());
        doctorDto.setMiddleName(user.getMiddleName());
        doctorDto.setLastName(user.getLastName());
        Doctor doctor = objectMapper.convertValue(doctorDto,Doctor.class);
        doctorRepository.save(doctor);
    }
}
