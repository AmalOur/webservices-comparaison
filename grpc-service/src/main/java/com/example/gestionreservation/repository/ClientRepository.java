package com.example.gestionreservation.repository;

import com.example.gestionreservation.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {}