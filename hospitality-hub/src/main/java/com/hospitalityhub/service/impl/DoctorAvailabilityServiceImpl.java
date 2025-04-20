package com.hospitalityhub.service.impl;

import com.hospitalityhub.entity.DoctorAvailability;
import com.hospitalityhub.repository.DoctorAvailabilityRepository;
import com.hospitalityhub.service.DoctorAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityServiceImpl implements DoctorAvailabilityService {
private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    @Override
    public DoctorAvailability doctorAvailability() {
        return null;
    }
}
