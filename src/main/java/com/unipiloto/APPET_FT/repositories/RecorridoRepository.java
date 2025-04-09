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

    @Query("SELECT COUNT(DISTINCT r.fecha) FROM Recorrido r " +
            "WHERE r.mascota.id_mascota = :mascotaId " +
            "AND MONTH(r.fecha) = :mes " +
            "AND YEAR(r.fecha) = :anio")
    Long countDistinctRecorridosByMascotaAndMonth(@Param("mascotaId") Integer mascotaId, @Param("mes") int mes,
            @Param("anio") int anio);

    @Query("SELECT r FROM Recorrido r WHERE r.mascota.id_mascota = :mascotaId AND r.fecha = :fecha ORDER BY r.id_recorrido ASC")
    List<Recorrido> findRecorridosByMascotaAndFecha(@Param("mascotaId") Integer mascotaId,
            @Param("fecha") Date fecha);
}
