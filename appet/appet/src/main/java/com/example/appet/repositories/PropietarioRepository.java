package com.example.appet.repositories;

import com.example.appet.models.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropietarioRepository extends JpaRepository<Propietario, Integer> {
    Optional<Propietario> findByCorreo(String correo);
}
