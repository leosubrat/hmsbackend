package com.hospitalityhub.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doctorId;
    private String firstName;
    private String middleName;
    private String lastName;
    private int experience;
    private int age;
    private String expertise;
    private String salary;
    private int panNumber;
}
//rimesh1@gmail.com
//Rimesh1@@