package com.unipiloto.APPET_FT.controllers;

import com.unipiloto.APPET_FT.models.Agenda;
import com.unipiloto.APPET_FT.services.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agenda")
public class AgendaController {
    @Autowired
    private AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @PostMapping("/registrar/{correo}/{idMascota}")
    public ResponseEntity<String> guardarAgenda(
            @RequestBody Agenda agenda,
            @PathVariable String correo,
            @PathVariable Integer idMascota) {

        String mensaje = agendaService.guardarAgenda(agenda, correo, idMascota);

        if (mensaje.startsWith("Error")) {
            return ResponseEntity.badRequest().body(mensaje);
        }
        return ResponseEntity.ok(mensaje);
    }

    @DeleteMapping("/eliminar/{correo}/{idAgenda}")
    public ResponseEntity<String> eliminarAgenda(@PathVariable String correo, @PathVariable Integer idAgenda) {
        String mensaje = agendaService.eliminarAgenda(correo, idAgenda);

        if (mensaje.startsWith("Error")) {
            return ResponseEntity.badRequest().body(mensaje);
        }
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
}
