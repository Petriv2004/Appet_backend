package com.unipiloto.APPET_FT.repositories;

import com.unipiloto.APPET_FT.models.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropietarioRepository extends JpaRepository<Propietario, Integer> {
    Optional<Propietario> findByCorreo(String correo);
}
