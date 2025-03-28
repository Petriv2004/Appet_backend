package com.unipiloto.APPET_FT.repositories;

import com.unipiloto.APPET_FT.models.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
