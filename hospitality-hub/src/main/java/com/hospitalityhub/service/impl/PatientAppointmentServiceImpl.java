package com.hospitalityhub.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientAppointmentServiceImpl implements PatientAppointmentService{
private final ObjectMapper objectMapper;
private final PatientAppointmentRepository patientAppointmentRepository;
private final DoctorNotificationService notificationService;
private final DoctorServiceImpl doctorService;
private  final DoctorRepository doctorRepository;
private final UserRepository userRepository;
    @Override
    public void savePatientAppointment(PatientAppointmentDTO patientAppointmentDTO, Principal principal) {
        if (patientAppointmentDTO==null){
            throw new NotFoundException("patientAppointment is not found");
        }
        String licenseNumber = patientAppointmentDTO.getLicenseNumber();
        Doctor byLicenseNumber = doctorRepository.findByLicenseNumber(licenseNumber);
        if (byLicenseNumber == null) {
            throw new NotFoundException("liscence number is not found");

        }
        PatientAppointment patientAppointment = objectMapper.convertValue(patientAppointmentDTO, PatientAppointment.class);
        PatientAppointment savedAppointment = patientAppointmentRepository.save(patientAppointment);
        notificationService.createAppointmentNotification(
                byLicenseNumber.getDoctorId(),
                patientAppointmentDTO.getPatientName(),
                savedAppointment.getId()
        );

    }

    @Override
    public List<PatientAppointmentDTO> getAppointmentsByUserEmail(String email) {
        List<PatientAppointmentDTO> patientAppointmentDTOList = new ArrayList<>();
        List<PatientAppointment> userAppointments = patientAppointmentRepository.findByPatientEmail(email);

        for (PatientAppointment patientAppointment : userAppointments) {
            PatientAppointmentDTO dto = new PatientAppointmentDTO();
            // Map all properties
            dto.setAppointmentDate(patientAppointment.getAppointmentDate());
            dto.setAppointmentTime(patientAppointment.getAppointmentTime());
            dto.setNewPatient(patientAppointment.isNewPatient());
            dto.setDoctorSpecialty(patientAppointment.getDoctorSpecialty());
            dto.setDoctorName(patientAppointment.getDoctorName());
            dto.setReasonForVisit(patientAppointment.getReasonForVisit());
            dto.setInsurance(patientAppointment.getInsurance());
            patientAppointmentDTOList.add(dto);
        }

        return patientAppointmentDTOList;
    }

    @Override
    public UserDto getUserDetails(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()){
            throw new NotFoundException("user is not found");
        }
        UserDto userDto = new UserDto();
        userDto.setFirstName(optionalUser.get().getFirstName());
        userDto.setMiddleName(optionalUser.get().getMiddleName());
        userDto.setLastName(optionalUser.get().getLastName());
        userDto.setPhone(optionalUser.get().getPhone());
        userDto.setEmail(optionalUser.get().getEmail());
        return userDto;
    }
}