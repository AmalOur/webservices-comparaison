package com.example.gestionreservation.repository;

import com.example.gestionreservation.entity.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ChambreRepository extends JpaRepository<Chambre, Long> {}
