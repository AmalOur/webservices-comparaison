package com.example.gestionreservation.service;

import com.example.gestionreservation.entity.Reservation;
import org.springframework.http.ResponseEntity;
import com.example.gestionreservation.dto.ReservationRequest;

public interface ReservationService {
        ResponseEntity<String> createReservation(ReservationRequest request);
        ResponseEntity<Reservation> getReservation(Long id);
        ResponseEntity<String> updateReservation(Long id, ReservationRequest request);
        ResponseEntity<String> deleteReservation(Long id);
}