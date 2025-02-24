package com.example.appet.controllers;

import com.example.appet.models.Mascota;
import com.example.appet.services.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarMascota(@RequestBody Mascota mascota) {
        Mascota nuevaMascota = mascotaService.registrarMascota(mascota);
        if(mascota != null){
            return ResponseEntity.ok("Mascota guardada exitosamente.");
        }

        return ResponseEntity.badRequest().body("Error al guardar mascota.");
    }
}
