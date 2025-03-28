package com.unipiloto.APPET_FT.repositories;

import com.unipiloto.APPET_FT.models.RitmoCardiaco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface RitmoCardiacoRepository extends JpaRepository<RitmoCardiaco, Integer> {
    @Query("SELECT r FROM RitmoCardiaco r WHERE r.mascota.id = :idMascota AND r.fecha = :fecha ORDER BY r.hora ASC")
    List<RitmoCardiaco> findByMascotaIdAndFecha(@Param("idMascota") int idMascota, @Param("fecha") Date fecha);
}
