package com.unipiloto.APPET_FT.services;

import com.unipiloto.APPET_FT.models.Agenda;
import com.unipiloto.APPET_FT.models.Mascota;
import com.unipiloto.APPET_FT.models.Propietario;
import com.unipiloto.APPET_FT.repositories.AgendaRepository;
import com.unipiloto.APPET_FT.repositories.MascotaRepository;
import com.unipiloto.APPET_FT.repositories.PropietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired
    private PropietarioRepository propietarioRepository;

    public AgendaService(AgendaRepository agendaRepository, MascotaRepository mascotaRepository, PropietarioRepository propietarioRepository) {
        this.agendaRepository = agendaRepository;
        this.mascotaRepository = mascotaRepository;
        this.propietarioRepository = propietarioRepository;
    }

    public String guardarAgenda(Agenda agenda, String correo, Integer idMascota) {
        Optional<Propietario> propietarioOpt = propietarioRepository.findByCorreo(correo);
        if (propietarioOpt.isEmpty()) {
            return "Error: Propietario no encontrado.";
        }

        Optional<Mascota> mascotaOpt = mascotaRepository.findById(idMascota);
        if (mascotaOpt.isEmpty()) {
            return "Error: Mascota no encontrada.";
        }

        Mascota mascota = mascotaOpt.get();
        Propietario propietario = propietarioOpt.get();

        if (!mascota.getPropietario().getId_propietario().equals(propietario.getId_propietario())) {
            return "Error: La mascota no pertenece al propietario con el correo proporcionado.";
        }

        agenda.setMascota(mascota);
        agendaRepository.save(agenda);
        return "Agenda guardada exitosamente.";
    }

    public List<Agenda> obtenerCitasPorPropietario(String correo) {
        Optional<Propietario> propietarioOpt = propietarioRepository.findByCorreo(correo);
        if (propietarioOpt.isEmpty()) {
            List<Agenda> vacia = new ArrayList<>();
            return vacia;
        }
        Propietario propietario = propietarioOpt.get();

        List<Mascota> mascotas = propietario.getMacotasList();

        List<Agenda> citas = new ArrayList<>();
        for (Mascota mascota : mascotas) {
            citas.addAll(mascota.getCitas());
        }
        return citas;
    }


    public String eliminarAgenda(String correo, Integer idAgenda) {
        Optional<Propietario> propietarioOpt = propietarioRepository.findByCorreo(correo);
        if (propietarioOpt.isEmpty()) {
            return "Error: Propietario no encontrado.";
        }
        Propietario propietario = propietarioOpt.get();

        Optional<Agenda> agendaOpt = agendaRepository.findById(idAgenda);
        if (agendaOpt.isEmpty()) {
            return "Error: La agenda con ID " + idAgenda + " no existe.";
        }
        Agenda agenda = agendaOpt.get();

        Mascota mascota = agenda.getMascota();

        if (mascota == null || !mascota.getPropietario().getId_propietario().equals(propietario.getId_propietario())) {
            return "Error: No tiene permiso para eliminar esta cita.";
        }
        agendaRepository.deleteById(idAgenda);
        return "Agenda eliminada exitosamente.";
    }

    public List<Agenda> obtenerCitasPorMascota(Integer idMascota) {
        return agendaRepository.findByIdMascota(idMascota);
    }
}
