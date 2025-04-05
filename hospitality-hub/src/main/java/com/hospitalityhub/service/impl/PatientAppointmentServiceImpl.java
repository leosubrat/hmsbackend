package com.hospitalityhub.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalityhub.dto.AppointmentDto;
import com.hospitalityhub.dto.DoctorDto;
import com.hospitalityhub.dto.PatientAppointmentDTO;
import com.hospitalityhub.dto.UserDto;
import com.hospitalityhub.entity.Doctor;
import com.hospitalityhub.entity.PatientAppointment;
import com.hospitalityhub.entity.User;
import com.hospitalityhub.repository.DoctorRepository;
import com.hospitalityhub.repository.PatientAppointmentRepository;
import com.hospitalityhub.repository.UserRepository;
import com.hospitalityhub.service.PatientAppointmentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientAppointmentServiceImpl {
}