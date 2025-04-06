package com.hospitalityhub.controllers;

import com.hospitalityhub.dto.PatientAppointmentDTO;
import com.hospitalityhub.service.PatientAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.hospitalityhub.shared.ApiURL.SAVE_PATIENT_APPOINTMENT;

@RestController
@RequiredArgsConstructor
public class PatientAppointmentController {
  private final PatientAppointmentService patientAppointmentService;

  @PostMapping(SAVE_PATIENT_APPOINTMENT)
  public ResponseEntity<?> savePatientAppointment(@RequestBody PatientAppointmentDTO patientAppointmentDTO){
      patientAppointmentService.savePatientAppointment(patientAppointmentDTO);
      return ResponseEntity.ok().body("succesfully save");
  }
}