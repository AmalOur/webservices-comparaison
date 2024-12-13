package com.example.gestionreservation.service;

import com.example.gestionreservation.entity.Chambre;
import com.example.gestionreservation.entity.Client;
import com.example.gestionreservation.entity.Reservation;
import com.example.gestionreservation.repository.ChambreRepository;
import com.example.gestionreservation.repository.ClientRepository;
import com.example.gestionreservation.repository.ReservationRepository;
import com.example.gestionreservation.stubs.Gestionreservation;
import com.example.gestionreservation.stubs.ReservationServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class ReservationGrpcService extends ReservationServiceGrpc.ReservationServiceImplBase {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final ChambreRepository chambreRepository;

    @Autowired
    public ReservationGrpcService(ReservationRepository reservationRepository, ClientRepository clientRepository, ChambreRepository chambreRepository) {
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
        this.chambreRepository = chambreRepository;
    }

    @Override
    public void getReservationById(Gestionreservation.ReservationRequest request, StreamObserver<Gestionreservation.ReservationResponse> responseObserver) {
        Reservation reservation = reservationRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Gestionreservation.ReservationResponse response = Gestionreservation.ReservationResponse.newBuilder()
                .setId(reservation.getId())
                .setClientId(reservation.getClient().getId())
                .setChambreId(reservation.getChambre().getId())
                .setDateDebut(reservation.getDateDebut().toString())
                .setDateFin(reservation.getDateFin().toString())
                .setPreferences(reservation.getPreferences())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createReservation(Gestionreservation.CreateReservationRequest request, StreamObserver<Gestionreservation.ReservationResponse> responseObserver) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Chambre chambre = chambreRepository.findById(request.getChambreId())
                .orElseThrow(() -> new RuntimeException("Chambre not found"));

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setChambre(chambre);
        reservation.setDateDebut(request.getDateDebut());
        reservation.setDateFin(request.getDateFin());
        reservation.setPreferences(request.getPreferences());

        Reservation savedReservation = reservationRepository.save(reservation);

        Gestionreservation.ReservationResponse response = Gestionreservation.ReservationResponse.newBuilder()
                .setId(savedReservation.getId())
                .setClientId(savedReservation.getClient().getId())
                .setChambreId(savedReservation.getChambre().getId())
                .setDateDebut(savedReservation.getDateDebut().toString())
                .setDateFin(savedReservation.getDateFin().toString())
                .setPreferences(savedReservation.getPreferences())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateReservation(Gestionreservation.UpdateReservationRequest request, StreamObserver<Gestionreservation.ReservationResponse> responseObserver) {
        Reservation reservation = reservationRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Chambre chambre = chambreRepository.findById(request.getChambreId())
                .orElseThrow(() -> new RuntimeException("Chambre not found"));

        reservation.setClient(client);
        reservation.setChambre(chambre);
        reservation.setDateDebut(request.getDateDebut());
        reservation.setDateFin(request.getDateFin());
        reservation.setPreferences(request.getPreferences());

        Reservation updatedReservation = reservationRepository.save(reservation);

        Gestionreservation.ReservationResponse response = Gestionreservation.ReservationResponse.newBuilder()
                .setId(updatedReservation.getId())
                .setClientId(updatedReservation.getClient().getId())
                .setChambreId(updatedReservation.getChambre().getId())
                .setDateDebut(updatedReservation.getDateDebut().toString())
                .setDateFin(updatedReservation.getDateFin().toString())
                .setPreferences(updatedReservation.getPreferences())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteReservation(Gestionreservation.DeleteReservationRequest request, StreamObserver<Gestionreservation.DeleteReservationResponse> responseObserver) {
        Reservation reservation = reservationRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservationRepository.delete(reservation);

        Gestionreservation.DeleteReservationResponse response = Gestionreservation.DeleteReservationResponse.newBuilder()
                .setMessage("Reservation deleted successfully")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
