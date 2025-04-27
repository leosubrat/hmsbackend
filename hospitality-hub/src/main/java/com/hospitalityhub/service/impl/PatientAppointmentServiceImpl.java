package com.hospitalityhub.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalityhub.dto.PatientAppointmentDTO;
import com.hospitalityhub.dto.UserDto;
import com.hospitalityhub.entity.Doctor;
import com.hospitalityhub.entity.DoctorAvailability;
import com.hospitalityhub.entity.PatientAppointment;
import com.hospitalityhub.entity.User;
import com.hospitalityhub.repository.DoctorAvailabilityRepository;
import com.hospitalityhub.repository.DoctorRepository;
import com.hospitalityhub.repository.PatientAppointmentRepository;
import com.hospitalityhub.repository.UserRepository;
import com.hospitalityhub.service.PatientAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientAppointmentServiceImpl implements PatientAppointmentService {
    private final ObjectMapper objectMapper;
    private final PatientAppointmentRepository patientAppointmentRepository;
    private final DoctorNotificationService notificationService;
    private final DoctorServiceImpl doctorService;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;

    @Override
    public void savePatientAppointment(PatientAppointmentDTO patientAppointmentDTO, Principal principal) {
        if (patientAppointmentDTO == null) {
            throw new NotFoundException("patientAppointment is not found");
        }
        String licenseNumber = patientAppointmentDTO.getLicenseNumber();
        Doctor byLicenseNumber = doctorRepository.findByLicenseNumber(licenseNumber);
        if (byLicenseNumber == null) {
            throw new NotFoundException("liscence number is not found");

        }
        PatientAppointment patientAppointment = objectMapper.convertValue(patientAppointmentDTO, PatientAppointment.class);
        patientAppointment.setStatus(false);
        List<PatientAppointment> all = patientAppointmentRepository.findAll();
        if (all.isEmpty()) {
            PatientAppointment savedAppointment = patientAppointmentRepository.save(patientAppointment);
            notificationService.createAppointmentNotification(
                    byLicenseNumber.getDoctorId(),
                    patientAppointmentDTO.getPatientName(),
                    savedAppointment.getId()
            );
        }
        for (PatientAppointment patientAppointment1 : all) {
            if (patientAppointment1.getAppointmentTime().equals(patientAppointmentDTO.getAppointmentTime())) {
                break;
            } else if (patientAppointment1.getPatientEmail().equals(patientAppointmentDTO.getPatientEmail())
                    && patientAppointment1.getDoctorSpecialty().equals(patientAppointmentDTO.getDoctorSpecialty()) && patientAppointment1.getAppointmentDate().equals(patientAppointmentDTO.getAppointmentDate())) {
                break;
            } else {
                PatientAppointment savedAppointment = patientAppointmentRepository.save(patientAppointment);
                notificationService.createAppointmentNotification(
                        byLicenseNumber.getDoctorId(),
                        patientAppointmentDTO.getPatientName(),
                        savedAppointment.getId()
                );
            }
        }

    }

    public List<PatientAppointment> patientAppointmentDTOList(String doctorEmail) {
        List<PatientAppointment> patientAppointmentsList = new ArrayList<>();
        Optional<User> user = userRepository.findByEmail(doctorEmail);
        System.out.println(user);
        if (user.isPresent()) {
            Optional<Doctor> doctor = doctorRepository.findByUser(user.get());
            List<PatientAppointment> patientAppointments = patientAppointmentRepository.findAll();
            for (PatientAppointment patientAppointment : patientAppointments) {
                if (doctor.isPresent() && !patientAppointment.isStatus()) {
                    if (doctor.get().getDoctorId() == patientAppointment.getDoctorId()) {
                        patientAppointmentsList.add(patientAppointment);
                    }
                }
            }
        }

        return patientAppointmentsList;
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
        List<Doctor> doctorList = doctorRepository.findAll();
        List<DoctorAvailability> doctorAvailabilities = doctorAvailabilityRepository.findAll();
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("user is not found");
        }
        List<DoctorAvailability> doctorAvailabilities1 = new ArrayList<>();
        for (Doctor doctor : doctorList) {
            for (DoctorAvailability doctorAvailability : doctorAvailabilities) {
                if (doctor.getDoctorId() == doctorAvailability.getDoctorId()) {
                    doctorAvailabilities1.add(doctorAvailability);
                }
            }
        }
        UserDto userDto = new UserDto();
        userDto.setDoctorAvailabilities(doctorAvailabilities1);
        userDto.setFirstName(optionalUser.get().getFirstName());
        userDto.setMiddleName(optionalUser.get().getMiddleName());
        userDto.setLastName(optionalUser.get().getLastName());
        userDto.setPhone(optionalUser.get().getPhone());
        userDto.setEmail(optionalUser.get().getEmail());
        return userDto;
    }
}