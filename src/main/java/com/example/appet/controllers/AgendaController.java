package com.example.appet.controllers;

import com.example.appet.models.Agenda;
import com.example.appet.services.AgendaService;
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

    @PostMapping("/registrar/{idMascota}")
    public ResponseEntity<String> guardarAgenda(@RequestBody Agenda agenda, @PathVariable Integer idMascota) {
        String mensaje = agendaService.guardarAgenda(agenda, idMascota);

        if (mensaje.startsWith("Error")) {
            return ResponseEntity.badRequest().body(mensaje);
        }
        return ResponseEntity.ok(mensaje);
    }

    @DeleteMapping("/eliminar/{idAgenda}")
    public ResponseEntity<String> eliminarAgenda(@PathVariable Integer idAgenda) {
        String mensaje = agendaService.eliminarAgenda(idAgenda);

        if (mensaje.startsWith("Error")) {
            return ResponseEntity.badRequest().body(mensaje);
        }
        return ResponseEntity.ok(mensaje);
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
