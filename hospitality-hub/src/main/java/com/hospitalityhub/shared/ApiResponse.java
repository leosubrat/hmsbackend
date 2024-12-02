package com.hospitalityhub.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Builder
@Service
public class ApiResponse {
    private String message;
}
