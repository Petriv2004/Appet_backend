package com.example.appet.repositories;

import com.example.appet.models.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MascotaRepository extends JpaRepository<Mascota, Integer> {
    Optional<Mascota> findById(Integer id_mascota);
}
