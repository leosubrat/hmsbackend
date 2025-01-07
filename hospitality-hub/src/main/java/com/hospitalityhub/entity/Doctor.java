package com.hospitalityhub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctor")
public class Doctor {
    private int id;
    private String doctorName;
    private String doctorMiddleName;
    private String doctorLastName;
    private int experience;
    private int age;
    private String expertise;
    private String salary;
}
