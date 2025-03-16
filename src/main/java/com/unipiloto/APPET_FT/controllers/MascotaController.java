package com.unipiloto.APPET_FT.controllers;

import com.unipiloto.APPET_FT.models.Mascota;
import com.unipiloto.APPET_FT.services.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Mascota> obtenerMascota(@PathVariable Integer id){
        Mascota mascota = mascotaService.obtenerMascota(id);
        if(mascota == null){
            return ResponseEntity.badRequest().build();
        }else{
            return  ResponseEntity.ok().body(mascota);
        }
    }

    @GetMapping("/obtenerRitmo/{id}/{estado}")
    public ResponseEntity<Object> obtenerRitmocardiaco(@PathVariable Integer id, @PathVariable Integer estado){
        Map<String, Integer> response = new HashMap<>();
        int respuesta = mascotaService.medirRitmoCardiaco(id, estado);
            response.put("mensaje", respuesta);
        return ResponseEntity.ok(response);
    }
}
