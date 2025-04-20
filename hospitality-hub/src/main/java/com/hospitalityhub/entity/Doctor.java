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
    private String doctorDescription;
    private String expertise;
    private String salary;
   private byte[] photo;
   private String email;
}
//rimesh1@gmail.com
//Rimesh1@@