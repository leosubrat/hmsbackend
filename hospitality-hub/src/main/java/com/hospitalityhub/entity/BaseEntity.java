package com.hospitalityhub.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {
    @JsonProperty(value = "createdDate")
    private Date createdDate;
    private Date modifiedDate;
}
