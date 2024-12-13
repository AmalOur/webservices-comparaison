package com.example.gestionreservation.service;

import com.example.gestionreservation.dto.ReservationRequest;
import com.example.gestionreservation.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@Service
@WebService(serviceName = "ReservationService")
public class ReservationSoapService {

    @Autowired
    private ReservationService reservationService;

    @WebMethod
    public String createReservation(@WebParam(name = "request") ReservationRequest request) {
        return reservationService.createReservation(request).getBody();
    }

    @WebMethod
    public Reservation getReservation(@WebParam(name = "reservationId") Long reservationId) {
        return reservationService.getReservation(reservationId).getBody();
    }

    @WebMethod
    public String updateReservation(@WebParam(name = "reservationId") Long reservationId, @WebParam(name = "request") ReservationRequest request) {
        return reservationService.updateReservation(reservationId, request).getBody();
    }

    @WebMethod
    public String deleteReservation(@WebParam(name = "reservationId") Long reservationId) {
        return reservationService.deleteReservation(reservationId).getBody();
    }
}
