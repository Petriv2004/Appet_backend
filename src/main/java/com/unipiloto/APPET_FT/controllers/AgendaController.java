package com.unipiloto.APPET_FT.controllers;

import com.unipiloto.APPET_FT.dtos.AgendaVeterinarioDTO;
import com.unipiloto.APPET_FT.models.Agenda;
import com.unipiloto.APPET_FT.services.AgendaService;
import com.unipiloto.APPET_FT.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agenda")
public class AgendaController {
    @Autowired
    private AgendaService agendaService;
    @Autowired
    private EmailService emailService;

    public AgendaController(AgendaService agendaService, EmailService emailService) {
        this.agendaService = agendaService;
        this.emailService = emailService;
    }

    @PostMapping("/registrar/{correo}/{idMascota}")
    public ResponseEntity<String> guardarAgenda(@RequestBody Agenda agenda, @PathVariable String correo, @PathVariable Integer idMascota) {
        String mensaje = agendaService.guardarAgenda(agenda, correo, idMascota);
        if (mensaje.startsWith("Error")) {
            return ResponseEntity.badRequest().body(mensaje);
        }
        String correoPropietario = agenda.getMascota().getPropietario().getCorreo();
        String mensajeCorreo = "Hola, " + agenda.getMascota().getPropietario().getNombre() +
                ". Tu cita para " + agenda.getMascota().getNombre() +
                " por motivo de " + agenda.getRazon() +
                " ha sido agendada para el " + agenda.getFecha() +
                " a las " + agenda.getHora() + ".";
        emailService.enviarCorreo(correo, "Se ha agendado correctamente su cita.", mensajeCorreo);
        return ResponseEntity.ok(mensaje);
    }

    @DeleteMapping("/eliminar/{correo}/{idAgenda}")
    public ResponseEntity<String> eliminarAgenda(@PathVariable String correo, @PathVariable Integer idAgenda) {
        Agenda cita = agendaService.obtenerCitaPorId(idAgenda);
        if (cita == null) {
            return ResponseEntity.badRequest().body("Error: La agenda con ID " + idAgenda + " no existe.");
        }
        String mensaje = agendaService.eliminarAgenda(correo, idAgenda);
        if (mensaje.startsWith("Error")) {
            return ResponseEntity.badRequest().body(mensaje);
        }
        String correoPropietario = cita.getMascota().getPropietario().getCorreo();
        String mensajeCorreo = "Hola, " + cita.getMascota().getPropietario().getNombre() +
                ". Te informamos que la cita de " + cita.getMascota().getNombre() +
                " por motivo de " + cita.getRazon() +
                " el " + cita.getFecha() + " a las " + cita.getHora() + " ha sido cancelada.";
        emailService.enviarCorreo(correoPropietario, "Se eliminó la cita de su mascota", mensajeCorreo);

        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/citas/{correo}")
    public ResponseEntity<?> obtenerCitasPorPropietario(@PathVariable String correo) {
        List<Agenda> citas = agendaService.obtenerCitasPorPropietario(correo);

        if (citas.isEmpty()) {
            return ResponseEntity.badRequest().body("No se encontraron citas para el propietario con correo: " + correo);
        }
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/mascota/{idMascota}")
    public ResponseEntity<List<Agenda>> obtenerCitasPorMascota(@PathVariable Integer idMascota) {
        List<Agenda> citas = agendaService.obtenerCitasPorMascota(idMascota);
        if (citas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/citas-veterinario/{correo}")
    public ResponseEntity<List<AgendaVeterinarioDTO>> obtenerCitasVeterinario(@PathVariable String correo){
        List<AgendaVeterinarioDTO> citas = agendaService.obtenerAgendaMascotasDeVeterinario(correo);
        if(!citas.isEmpty()){
            return ResponseEntity.ok(citas);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/cambiar-asistido/{idAgenda}")
    public ResponseEntity<Agenda> cambiarEstadoAsistido(@PathVariable Integer idAgenda){
        Agenda cita = agendaService.cambiarEstadoAsistido(idAgenda);

        if(cita != null){
            return ResponseEntity.ok(cita);
        }
        return ResponseEntity.badRequest().build();
    }
}
