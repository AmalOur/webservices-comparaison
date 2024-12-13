package com.example.gestionreservation.service;

import com.example.gestionreservation.dto.ReservationRequest;
import com.example.gestionreservation.entity.Chambre;
import com.example.gestionreservation.entity.Client;
import com.example.gestionreservation.entity.Reservation;
import com.example.gestionreservation.repository.ChambreRepository;
import com.example.gestionreservation.repository.ClientRepository;
import com.example.gestionreservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    @Override
    public ResponseEntity<String> createReservation(ReservationRequest request) {
        Optional<Client> clientOptional = clientRepository.findById(request.getClientId());
        Optional<Chambre> chambreOptional = chambreRepository.findById(request.getChambreId());

        if (clientOptional.isPresent() && chambreOptional.isPresent()) {
            Client client = clientOptional.get();
            Chambre chambre = chambreOptional.get();

            Reservation reservation = new Reservation();
            reservation.setClient(client);
            reservation.setChambre(chambre);
            reservation.setDateDebut(request.getDateDebut());
            reservation.setDateFin(request.getDateFin());
            reservation.setPreferences(request.getPreferences());

            chambre.setDisponible(false);
            chambreRepository.save(chambre);
            reservationRepository.save(reservation);

            return ResponseEntity.status(HttpStatus.CREATED).body("Réservation créée avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client ou chambre non trouvé");
        }
    }

    @Override
    public ResponseEntity<Reservation> getReservation(Long id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        return reservationOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public ResponseEntity<String> updateReservation(Long id, ReservationRequest request) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        Optional<Client> clientOptional = clientRepository.findById(request.getClientId());
        Optional<Chambre> chambreOptional = chambreRepository.findById(request.getChambreId());

        if (reservationOptional.isPresent() && clientOptional.isPresent() && chambreOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            Client client = clientOptional.get();
            Chambre chambre = chambreOptional.get();

            reservation.setClient(client);
            reservation.setChambre(chambre);
            reservation.setDateDebut(request.getDateDebut());
            reservation.setDateFin(request.getDateFin());
            reservation.setPreferences(request.getPreferences());

            chambre.setDisponible(false);
            chambreRepository.save(chambre);
            reservationRepository.save(reservation);

            return ResponseEntity.ok("Réservation mise à jour avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Réservation, client ou chambre non trouvé");
        }
    }

    @Override
    public ResponseEntity<String> deleteReservation(Long id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);

        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            Chambre chambre = reservation.getChambre();
            chambre.setDisponible(true);
            chambreRepository.save(chambre);
            reservationRepository.deleteById(id);
            return ResponseEntity.ok("Réservation supprimée avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Réservation non trouvée");
        }
    }
}
