package com.unipiloto.APPET_FT.controllers;

import com.unipiloto.APPET_FT.dtos.UbicacionDTO;
import com.unipiloto.APPET_FT.models.Mascota;
import com.unipiloto.APPET_FT.models.Recorrido;
import com.unipiloto.APPET_FT.services.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
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
        Mascota mascota = mascotaService.medirRitmoCardiaco(id,estado);
        if(mascota == null){
            return ResponseEntity.badRequest().build();
        }else{
            return  ResponseEntity.ok().body(mascota);
        }
    }

    @GetMapping("/recorridoPorFecha/{id_Mascota}/{fecha}")
    public ResponseEntity<List<Recorrido>> recorridoPorFecha(@PathVariable Integer id_Mascota, @PathVariable Date fecha){
        List<Recorrido> recorrido = mascotaService.recorridoPorFecha(id_Mascota, fecha);
        if(recorrido != null){
            return ResponseEntity.ok(recorrido);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/registrarRecorrido/{idMascota}")
    public ResponseEntity<Object> registrarRecorrido(@PathVariable Integer idMascota, @RequestBody List<Recorrido> recorridos) {
        String response = mascotaService.registrarRecorridos(idMascota, recorridos);
        Map<String, String> respuesta = new HashMap<>();
        if (response != null) {
            respuesta.put("mensaje","Guardado correctamenrte");
            return ResponseEntity.ok(response);
        }
        respuesta.put("mensaje","Error al guardar el recorrido");
        return ResponseEntity.badRequest().body(respuesta);
    }

    @GetMapping("/obtenerUbicacion/{idMascota}")
    public ResponseEntity<UbicacionDTO> obtenerUbicacion(@PathVariable Integer idMascota) {
        Mascota mascota = mascotaService.actualizarUbicacion(idMascota);
        UbicacionDTO ubicacion = new UbicacionDTO(mascota.getLatitud(), mascota.getLongitud());

        if(mascota != null){
            return ResponseEntity.ok(ubicacion);
        }
        return ResponseEntity.badRequest().build();
    }
}
