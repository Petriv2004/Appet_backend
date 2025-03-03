package com.unipiloto.APPET_FT.controllers;

import com.unipiloto.APPET_FT.models.Historial;
import com.unipiloto.APPET_FT.services.HistorialService;
import com.unipiloto.APPET_FT.services.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/historiales")
public class HistorialController {
    @Autowired
    private HistorialService historialService;
    @Autowired
    private MascotaService mascotaService;

    public HistorialController(HistorialService historialService) {
        this.historialService = historialService;
    }

    @PostMapping("/registrar/{idMascota}")
    public ResponseEntity<String> registrarHistorial(@RequestBody Historial historial, @PathVariable Integer idMascota) {
        String mensaje = historialService.registrarHistorial(historial, idMascota);
        if (mensaje.startsWith("Error")) {
            return ResponseEntity.badRequest().body(mensaje);
        }
        return ResponseEntity.ok(mensaje);
    }

    @PutMapping("/actualizar/{idMascota}")
    public ResponseEntity<String> actualizarHistorial(@PathVariable Integer idMascota, @RequestBody Historial datosActualizados) {
        String respuesta = mascotaService.actualizarHistorialPorMascota(idMascota, datosActualizados);
        if (!respuesta.contains(respuesta)) {
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }
}
