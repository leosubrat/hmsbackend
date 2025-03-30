package com.hospitalityhub.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "doctor")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doctorId;
    private String specialization;
    private String licenseNumber;
    private Integer yearsOfExperience;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
//rimesh1@gmail.com
//Rimesh1@@