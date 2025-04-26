package com.hospitalityhub.entity;

import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonObject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "admin_package")
@Getter
@Setter
public class AdminPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer packageId;
    private String packageName;
    private Integer packagePrice;
    private String description;
    private String testType;
    private boolean status;
}
