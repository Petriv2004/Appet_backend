package com.example.appet.repositories;

import com.example.appet.models.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
    @Query("SELECT a FROM Agenda a WHERE a.mascota.id_mascota = :idMascota")
    List<Agenda> findByIdMascota(@Param("idMascota") Integer idMascota);
}
