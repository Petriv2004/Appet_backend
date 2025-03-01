package com.unipiloto.APPET_FT.controllers;

import com.unipiloto.APPET_FT.models.Mascota;
import com.unipiloto.APPET_FT.services.MascotaService;
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
    public ResponseEntity<Mascota> registrarMascota(@RequestBody Mascota mascota) {
        Mascota nuevaMascota = mascotaService.registrarMascota(mascota);
        if (nuevaMascota != null) {
            return ResponseEntity.ok(nuevaMascota);
        }
        return ResponseEntity.badRequest().build();
    }

}
