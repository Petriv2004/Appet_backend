package com.unipiloto.APPET_FT.repositories;

import com.unipiloto.APPET_FT.models.Recorrido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface RecorridoRepository extends JpaRepository<Recorrido, Integer> {
    @Query("SELECT r FROM Recorrido r WHERE r.mascota.id_mascota = :idMascota AND r.fecha = :fecha")
    List<Recorrido> findByMascotaAndFecha(@Param("idMascota") Integer idMascota, @Param("fecha") Date fecha);
}
