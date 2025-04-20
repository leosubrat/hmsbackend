package com.hospitalityhub.repository;

import com.hospitalityhub.entity.Doctor;
import com.hospitalityhub.entity.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {

    List<DoctorAvailability> findByDoctorIdAndDate(int doctorId, LocalDate date);

    List<DoctorAvailability> findByDoctorIdAndDateGreaterThanEqual(int doctorId, LocalDate date);

    void deleteByDoctorIdAndDate(int doctorId, LocalDate date);

    Optional<Doctor> findById(Optional<Doctor> byId);
}