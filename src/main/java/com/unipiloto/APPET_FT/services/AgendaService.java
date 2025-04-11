package com.unipiloto.APPET_FT.services;

import com.unipiloto.APPET_FT.dtos.AgendaVeterinarioDTO;
import com.unipiloto.APPET_FT.models.Agenda;
import com.unipiloto.APPET_FT.models.Mascota;
import com.unipiloto.APPET_FT.models.Propietario;
import com.unipiloto.APPET_FT.repositories.AgendaRepository;
import com.unipiloto.APPET_FT.repositories.MascotaRepository;
import com.unipiloto.APPET_FT.repositories.PropietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
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
    @Autowired
    private EmailService emailService;

    public AgendaService(AgendaRepository agendaRepository, MascotaRepository mascotaRepository, PropietarioRepository propietarioRepository, EmailService emailService) {
        this.agendaRepository = agendaRepository;
        this.mascotaRepository = mascotaRepository;
        this.propietarioRepository = propietarioRepository;
        this.emailService = emailService;
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

        java.util.Date fechaUtil = new java.util.Date(agenda.getFecha().getTime());
        Instant instant = fechaUtil.toInstant();
        LocalDate fechaLocal = instant.atZone(ZoneId.of("UTC")).toLocalDate();
        java.sql.Date fechaSql = java.sql.Date.valueOf(fechaLocal);

        agenda.setFecha(fechaSql);
        agendaRepository.save(agenda);

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

    public Agenda obtenerCitaPorId(Integer id){
        Optional<Agenda> cita = agendaRepository.findById(id);
        if(cita.isPresent()){
            return cita.get();
        }
        return null;
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

    @Scheduled(cron = "0 0 * * * ?")
    public void enviarRecordatorios() {
        LocalDate hoy = LocalDate.now();
        LocalTime horaActual = LocalTime.now();
        List<Agenda> citasProximas = agendaRepository.findByFechaAndHora(
                Date.valueOf(hoy),
                Time.valueOf(horaActual),
                Time.valueOf(horaActual.plusHours(1))
        );
        for (Agenda cita : citasProximas) {
            try {
                String correoPropietario = cita.getMascota().getPropietario().getCorreo();
                String mensaje = "Hola, " + cita.getMascota().getPropietario().getNombre() +
                        ". Recuerda que tienes una cita para " + cita.getMascota().getNombre() +
                        " por motivo de " + cita.getRazon() +
                        " el " + cita.getFecha() + " a las " + cita.getHora() + ".";
                emailService.enviarCorreo(correoPropietario, "Recordatorio de cita", mensaje);
                cita.setEstado(true);
                agendaRepository.save(cita);
            } catch (Exception e) {
                System.err.println("Error enviando correo: " + e.getMessage());
            }
        }
    }

    public List<AgendaVeterinarioDTO> obtenerAgendaMascotasDeVeterinario(String correoVeterinario) {
        List<Agenda> agendas = agendaRepository.findAgendasByVeterinarioCorreoAndRazon(correoVeterinario, "Veterinario");
        List <AgendaVeterinarioDTO> respuesta = new ArrayList<>();
        for(Agenda a : agendas){
            Mascota mascotaAgenda = a.getMascota();
            Optional <Mascota> mascotas = mascotaRepository.findById(mascotaAgenda.getId_mascota());
            Mascota mascota = mascotas.get();
            respuesta.add(new AgendaVeterinarioDTO(a.getId_agenda(), a.getFecha(), a.getHora(), a.getRazon(), a.getDescripcion(), a.isAsistencia(), mascota.getNombre()));
        }
        return respuesta;
    }

    public Agenda cambiarEstadoAsistido(Integer idAgenda){
        Optional <Agenda> citas = agendaRepository.findById(idAgenda);
        if(citas.isPresent()){
            Agenda cita = citas.get();
            cita.setAsistencia(true);
            agendaRepository.save(cita);
            return cita;
        }
        return null;
    }
}
