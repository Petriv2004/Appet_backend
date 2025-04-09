package com.unipiloto.APPET_FT.repositories;

import com.unipiloto.APPET_FT.models.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
        Optional<Agenda> findById(Integer id_agenda);

        @Query("SELECT a FROM Agenda a WHERE a.mascota.id_mascota = :idMascota")
        List<Agenda> findByIdMascota(@Param("idMascota") Integer idMascota);

        @Query("SELECT a FROM Agenda a WHERE a.fecha = :fecha AND a.hora BETWEEN :horaActual AND :horaLimite AND a.estado = false")
        List<Agenda> findByFechaAndHora(@Param("fecha") Date fecha,
                        @Param("horaActual") Time horaActual,
                        @Param("horaLimite") Time horaLimite);

        @Query("SELECT a FROM Agenda a " +
                        "JOIN a.mascota m " +
                        "JOIN m.propietario p " +
                        "JOIN p.veterinarios v " +
                        "WHERE v.correo = :correoVeterinario " +
                        "AND a.razon = :razon")
        List<Agenda> findAgendasByVeterinarioCorreoAndRazon(@Param("correoVeterinario") String correoVeterinario,
                        @Param("razon") String razon);

        @Query("SELECT COUNT(a) FROM Agenda a " +
                        "WHERE a.mascota.id_mascota = :mascotaId " +
                        "AND a.razon = 'Veterinario' " +
                        "AND a.asistencia = true")
        Long countVisitasVeterinarioByMascota(@Param("mascotaId") Integer mascotaId);
}
