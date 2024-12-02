package com.hospitalityhub.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtResponse implements Serializable {
  private String accessToken;
  private String refreshToken;
  private String message;
}
