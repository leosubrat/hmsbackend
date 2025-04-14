package com.hospitalityhub.repository;

import com.hospitalityhub.entity.Doctor;
import com.hospitalityhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Integer> {


    Doctor findByLicenseNumber(String licenseNumber);


    Optional<Doctor> findByUser(User user);
}
