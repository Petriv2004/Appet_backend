package com.unipiloto.APPET_FT.repositories;

import com.unipiloto.APPET_FT.models.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MascotaRepository extends JpaRepository<Mascota, Integer> {
    Optional<Mascota> findById(Integer id_mascota);

    @Query("SELECT m.propietario.correo FROM Mascota m WHERE m.id_mascota = :idMascota")
    String findCorreoByIdMascota(@Param("idMascota") Integer idMascota);
}
