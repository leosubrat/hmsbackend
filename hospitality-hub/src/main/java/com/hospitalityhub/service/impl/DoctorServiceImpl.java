package com.hospitalityhub.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalityhub.dao.GeneralDao;
import com.hospitalityhub.dto.DoctorDto;
import com.hospitalityhub.entity.Doctor;
import com.hospitalityhub.entity.DoctorAvailability;
import com.hospitalityhub.entity.User;
import com.hospitalityhub.exception.UserAlreadyExistException;
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
            doctorDtoList.add(doctorDto);
        }
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

    public DoctorDto getDoctorByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Doctor doctor = doctorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        DoctorDto doctorDto = mapToDto(doctor);

        List<DoctorAvailability> availabilities = availabilityRepository.findByDoctorIdAndDateGreaterThanEqual(
                doctor.getDoctorId(), LocalDate.now());

        Map<LocalDate, List<DoctorAvailability>> availabilitiesByDate = availabilities.stream()
                .collect(Collectors.groupingBy(DoctorAvailability::getDate));

        LocalDate today = LocalDate.now();
        if (availabilitiesByDate.containsKey(today)) {
            doctorDto.setTodayAvailability(mapAvailabilityToTimeSlots(availabilitiesByDate.get(today))
            );
        }

        LocalDate tomorrow = today.plusDays(1);
        if (availabilitiesByDate.containsKey(tomorrow)) {
            doctorDto.setTomorrowAvailability(
                    mapAvailabilityToTimeSlots(availabilitiesByDate.get(tomorrow))
            );
        }

        return doctorDto;
    }

    @Transactional
    public DoctorDto updateDoctorProfile(String username, DoctorDto doctorDto) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        Doctor doctor = doctorRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Doctor not found"));//        doctor.setDoctorDescription(doctorDto.getDescription());
        doctor.setDoctorDescription(doctorDto.getDescription());
        Doctor updatedDoctor = doctorRepository.save(doctor);
        updateAvailability(updatedDoctor.getDoctorId(), LocalDate.now(), doctorDto.getTodayAvailability());
        updateAvailability(updatedDoctor.getDoctorId(), LocalDate.now().plusDays(1), doctorDto.getTomorrowAvailability());
        return getDoctorByUsername(username);
    }

    private void updateAvailability(int doctorId, LocalDate date, List<Map<String, String>> timeSlots) {
        if (timeSlots == null || timeSlots.isEmpty()) {
            return;
        }
        availabilityRepository.deleteByDoctorIdAndDate(doctorId, date);
        List<DoctorAvailability> newAvailabilities = new ArrayList<>();

        for (Map<String, String> slot : timeSlots) {
            LocalTime startTime = LocalTime.parse(slot.get("startTime"));
            LocalTime endTime = LocalTime.parse(slot.get("endTime"));

            DoctorAvailability availability = new DoctorAvailability();
            availability.setDoctorId(doctorId);
            availability.setDate(date);
            availability.setStartTime(startTime);
            availability.setEndTime(endTime);

            newAvailabilities.add(availability);
        }

        availabilityRepository.saveAll(newAvailabilities);
    }

    private List<Map<String, String>> mapAvailabilityToTimeSlots(List<DoctorAvailability> availabilities) {
        return availabilities.stream()
                .map(a -> Map.of(
                        "startTime", a.getStartTime().toString(),
                        "endTime", a.getEndTime().toString()
                ))
                .collect(Collectors.toList());
    }

    private DoctorDto mapToDto(Doctor doctor) {
        DoctorDto dto = new DoctorDto();
        dto.setDoctorId(doctor.getDoctorId());
        dto.setFirstName(doctor.getUser().getFirstName());
        dto.setLastName(doctor.getUser().getLastName());
        dto.setExperience(doctor.getYearsOfExperience());
        dto.setExpertise(doctor.getExpertise());
        dto.setSalary(doctor.getSalary());
        dto.setDescription(doctor.getDoctorDescription());
        return dto;
    }
    public DoctorDto findDoctorByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceAccessException("User not found with email: " + username));

        Doctor doctor = doctorRepository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceAccessException("Doctor profile not found for user: " + username));

        // Convert and return as DTO
        return mapToDto(doctor);
    }

}

