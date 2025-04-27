package com.hospitalityhub.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AdminPackageDTO {
        private Integer packageId;
        private String packageName;
        private Integer packagePrice;
        private String description;
        private String testType;
        private String firstName;
        private String lastName;
        private String phoneNumber;
}
