package com.example.gestionreservation.controller;

import com.example.gestionreservation.dto.ReservationRequest;
import com.example.gestionreservation.entity.Reservation;
import com.example.gestionreservation.entity.Client;
import com.example.gestionreservation.entity.Chambre;
import com.example.gestionreservation.repository.ReservationRepository;
import com.example.gestionreservation.repository.ClientRepository;
import com.example.gestionreservation.repository.ChambreRepository;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ReservationGraphQLController {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final ChambreRepository chambreRepository;

    @QueryMapping
    public List<Reservation> allReservations() {
        return reservationRepository.findAll();
    }

    @QueryMapping
    public Reservation getReservation(@Argument Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        return reservation.orElse(null);
    }


    @MutationMapping
    public Reservation createReservation(@Argument ReservationRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException(String.format("Client %s not found", request.getClientId())));
        Chambre chambre = chambreRepository.findById(request.getChambreId())
                .orElseThrow(() -> new RuntimeException(String.format("Chambre %s not found", request.getChambreId())));

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setChambre(chambre);
        reservation.setDateDebut(request.getDateDebut());
        reservation.setDateFin(request.getDateFin());
        reservation.setPreferences(request.getPreferences());
        return reservationRepository.save(reservation);
    }

    @MutationMapping
    public Reservation updateReservation(@Argument Long id, @Argument ReservationRequest request) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Reservation %s not found", id)));
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException(String.format("Client %s not found", request.getClientId())));
        Chambre chambre = chambreRepository.findById(request.getChambreId())
                .orElseThrow(() -> new RuntimeException(String.format("Chambre %s not found", request.getChambreId())));

        reservation.setClient(client);
        reservation.setChambre(chambre);
        reservation.setDateDebut(request.getDateDebut());
        reservation.setDateFin(request.getDateFin());
        reservation.setPreferences(request.getPreferences());
        return reservationRepository.save(reservation);
    }

    @MutationMapping
    public String deleteReservation(@Argument Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Reservation %s not found", id)));
        reservationRepository.delete(reservation);
        return "Reservation supprimée avec succès";
    }
}