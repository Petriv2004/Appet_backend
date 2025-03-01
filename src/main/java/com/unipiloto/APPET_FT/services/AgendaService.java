package com.unipiloto.APPET_FT.services;

import com.unipiloto.APPET_FT.models.Agenda;
import com.unipiloto.APPET_FT.models.Mascota;
import com.unipiloto.APPET_FT.repositories.AgendaRepository;
import com.unipiloto.APPET_FT.repositories.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private MascotaRepository mascotaRepository;

    public AgendaService(AgendaRepository agendaRepository, MascotaRepository mascotaRepository) {
        this.agendaRepository = agendaRepository;
        this.mascotaRepository = mascotaRepository;
    }

    public String guardarAgenda(Agenda agenda, Integer idMascota) {
        Optional<Mascota> mascotaOpt = mascotaRepository.findById(idMascota);
        if (mascotaOpt.isEmpty()) {
            return "Error: Mascota no encontrada.";
        }
        agenda.setMascota(mascotaOpt.get());
        agendaRepository.save(agenda);
        return "Agenda guardada exitosamente.";
    }

    public String eliminarAgenda(Integer idAgenda) {
        if (agendaRepository.existsById(idAgenda)) {
            agendaRepository.deleteById(idAgenda);
            return "Agenda eliminada exitosamente.";
        }
        return "Error: La agenda con ID " + idAgenda + " no existe.";
    }

    public List<Agenda> obtenerCitasPorMascota(Integer idMascota) {
        return agendaRepository.findByIdMascota(idMascota);
    }
}
