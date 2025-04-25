package com.hospitalityhub.repository;

import com.hospitalityhub.entity.DoctorNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorNotificationRepository extends JpaRepository<DoctorNotification, Long> {

    List<DoctorNotification> findByDoctorIdOrderByCreatedAtDesc(Long doctorId);

    @Query("SELECT COUNT(n) FROM DoctorNotification n JOIN PatientAppointment p ON n.appointmentId = p.id WHERE n.doctorId = :doctorId AND n.isRead = false AND p.status = false")
    Long countUnreadNotifications(@Param("doctorId") Long doctorId);
}
