package com.unipiloto.APPET_FT.repositories;

import com.unipiloto.APPET_FT.models.Mascota;
import com.unipiloto.APPET_FT.models.Recorrido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface MascotaRepository extends JpaRepository<Mascota, Integer> {
    Optional<Mascota> findById(Integer id_mascota);

    @Query("SELECT m.propietario.correo FROM Mascota m WHERE m.id_mascota = :idMascota")
    String findCorreoByIdMascota(@Param("idMascota") Integer idMascota);

    @Query("SELECT m " +
            "FROM Mascota m LEFT JOIN m.recorridos r " +
            "WHERE m.propietario.correo = :correo " +
            "GROUP BY m " +
            "ORDER BY COUNT(DISTINCT CASE WHEN MONTH(r.fecha) = :mes AND YEAR(r.fecha) = :anio THEN r.fecha ELSE NULL END) ASC")
    List<Mascota> findMascotasMasSedentarias(@Param("correo") String correo,
            @Param("mes") int mes,
            @Param("anio") int anio);

            @Query("SELECT m " +
            "FROM Mascota m LEFT JOIN m.recorridos r " +
            "WHERE m.propietario.correo = :correo " +
            "GROUP BY m " +
            "ORDER BY COUNT(DISTINCT CASE WHEN MONTH(r.fecha) = :mes AND YEAR(r.fecha) = :anio THEN r.fecha ELSE NULL END) DESC")
    List<Mascota> findMascotasMasActivas(@Param("correo") String correo,
            @Param("mes") int mes,
            @Param("anio") int anio);
}
