package com.hospitalityhub.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalityhub.dao.GeneralDao;
import com.hospitalityhub.dto.DoctorDto;
import com.hospitalityhub.entity.Doctor;
import com.hospitalityhub.entity.DoctorAvailability;
import com.hospitalityhub.entity.User;
import com.hospitalityhub.exception.UserAlreadyExistException;
import com.hospitalityhub.exception.UserNotFoundException;
import com.hospitalityhub.repository.DoctorAvailabilityRepository;
import com.hospitalityhub.repository.DoctorRepository;
import com.hospitalityhub.repository.UserRepository;
import com.hospitalityhub.shared.ResponseMessageConstant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl {
    private final GeneralDao generalDao;
    private final DoctorRepository doctorRepository;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final DoctorAvailabilityRepository availabilityRepository;

    public List<DoctorDto> findAllDoctor() {
        List<Doctor> doctorList = doctorRepository.findAll();
        List<DoctorDto> doctorDtoList = new ArrayList<>();
        for (Doctor doctor : doctorList) {
            DoctorDto doctorDto = new DoctorDto();
            doctorDto.setFirstName(doctor.getUser().getFirstName());
            doctorDto.setLastName(doctor.getUser().getLastName());
            doctorDto.setExperience(doctor.getYearsOfExperience());
            doctorDto.setExpertise(doctor.getSpecialization());
            doctorDto.setDescription(doctor.getDoctorDescription());
            doctorDto.setLicenseNumber(doctor.getLicenseNumber());
            doctorDtoList.add(doctorDto);
        }
        return doctorDtoList;
    }

    @Transactional
    public DoctorDto updateDoctorProfile(String username, DoctorDto doctorDto) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Doctor doctor = doctorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Update doctor description
        doctor.setDoctorDescription(doctorDto.getDescription());
        Doctor updatedDoctor = doctorRepository.save(doctor);

        if (doctorDto.getSelectedDate() != null && doctorDto.getTimeSlots() != null) {
            LocalDate selectedDate = LocalDate.parse(doctorDto.getSelectedDate());

            // Delete existing availability for this date
            availabilityRepository.deleteByDoctorIdAndDate(doctor.getDoctorId(), selectedDate);

            // Create and save new availability entries
            List<DoctorAvailability> availabilities = new ArrayList<>();
            for (Map<String, String> slot : doctorDto.getTimeSlots()) {
                DoctorAvailability availability = new DoctorAvailability();
                availability.setDoctorId(doctor.getDoctorId());
                availability.setDate(selectedDate);
                availability.setStartTime(LocalTime.parse(slot.get("startTime")));
                availability.setEndTime(LocalTime.parse(slot.get("endTime")));
                availabilities.add(availability);
            }

            if (!availabilities.isEmpty()) {
                availabilityRepository.saveAll(availabilities);
            }
        }

        DoctorDto updatedDoctorDto = mapToDto(updatedDoctor);

        // Include the updated time slots in the response
        if (doctorDto.getSelectedDate() != null) {
            LocalDate selectedDate = LocalDate.parse(doctorDto.getSelectedDate());
            List<DoctorAvailability> availabilities =
                    availabilityRepository.findByDoctorIdAndDate(doctor.getDoctorId(), selectedDate);

            updatedDoctorDto.setTimeSlots(mapAvailabilityToTimeSlots(availabilities));
            updatedDoctorDto.setSelectedDate(doctorDto.getSelectedDate());
        }

        return updatedDoctorDto;
    }

    private DoctorDto mapToDto(Doctor doctor) {
        DoctorDto dto = new DoctorDto();

        // Map basic properties
        dto.setDoctorId(doctor.getDoctorId());

        if (doctor.getUser() != null) {
            dto.setFirstName(doctor.getUser().getFirstName());
            dto.setMiddleName(doctor.getUser().getMiddleName());
            dto.setLastName(doctor.getUser().getLastName());
        }
        dto.setExperience(doctor.getYearsOfExperience());
        dto.setExpertise(doctor.getExpertise());
        dto.setSalary(doctor.getSalary());
        dto.setDescription(doctor.getDoctorDescription());
        dto.setLicenseNumber(doctor.getLicenseNumber());


        return dto;
    }


    @Transactional
    public Map<String, List<Map<String, String>>> updateAvailabilityForDate(int doctorId, String dateStr, List<Map<String, String>> timeSlots) {
        LocalDate date = LocalDate.parse(dateStr);

        availabilityRepository.deleteByDoctorIdAndDate(doctorId, date);

        // Create and save new availability records
        List<DoctorAvailability> availabilities = new ArrayList<>();
        for (Map<String, String> slot : timeSlots) {
            DoctorAvailability availability = new DoctorAvailability();
            availability.setDoctorId(doctorId);
            availability.setDate(date);
            availability.setStartTime(LocalTime.parse(slot.get("startTime")));
            availability.setEndTime(LocalTime.parse(slot.get("endTime")));
            availabilities.add(availability);
        }

        if (!availabilities.isEmpty()) {
            availabilityRepository.saveAll(availabilities);
        }

        // Retrieve and return the updated availability
        List<DoctorAvailability> updatedAvailabilities = availabilityRepository.findByDoctorIdAndDate(doctorId, date);
        List<Map<String, String>> updatedTimeSlots = mapAvailabilityToTimeSlots(updatedAvailabilities);

        // Return as a map with the date as key
        return Map.of(dateStr, updatedTimeSlots);
    }
    private List<Map<String, String>> mapAvailabilityToTimeSlots(List<DoctorAvailability> availabilities) {
        return availabilities.stream()
                .map(a -> Map.of(
                        "startTime", a.getStartTime().toString(),
                        "endTime", a.getEndTime().toString()
                ))
                .collect(Collectors.toList());
    }
    public DoctorDto findDoctorByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceAccessException("User not found with email: " + username));

        Doctor doctor = doctorRepository.findByUser(user)
                .orElseThrow(() -> new ResourceAccessException("Doctor profile not found for user: " + username));

        // Convert and return as DTO
        return mapToDto(doctor);
    }

}