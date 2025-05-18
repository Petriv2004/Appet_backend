package com.unipiloto.APPET_FT.controllers;

import com.unipiloto.APPET_FT.models.Historial;
import com.unipiloto.APPET_FT.models.Mascota;
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
    public ResponseEntity<Mascota> registrarHistorial(@RequestBody Historial historial, @PathVariable Integer idMascota) {
        Mascota mensaje = historialService.registrarHistorial(historial, idMascota);

        if (mensaje == null) {
            return ResponseEntity.badRequest().body(mensaje);
        }
        return ResponseEntity.ok(mensaje);
    }

    @PutMapping("/actualizar/{idMascota}")
    public ResponseEntity<Mascota> actualizarHistorial(@PathVariable Integer idMascota, @RequestBody Historial datosActualizados) {
        Mascota respuesta = mascotaService.actualizarHistorialPorMascota(idMascota, datosActualizados);
        if (respuesta == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(respuesta);
    }

    @GetMapping("/obtener/{idMascota}")
    public ResponseEntity<Historial> obtenerHistorial(@PathVariable Integer idMascota) {
        Historial respuesta = historialService.obtenerHistorial(idMascota);
        if (respuesta == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(respuesta);
    }

    @DeleteMapping("/eliminar/{idMascota}")
    public ResponseEntity<Historial> eliminarHistorial(@PathVariable Integer idMascota) {
        Historial respuesta = historialService.eliminarHistorial(idMascota);
        if (respuesta == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(respuesta);
    }
}
