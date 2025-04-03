package com.unipiloto.APPET_FT.controllers;

import com.unipiloto.APPET_FT.dtos.MascotaDTO;
import com.unipiloto.APPET_FT.dtos.UbicacionDTO;
import com.unipiloto.APPET_FT.models.Mascota;
import com.unipiloto.APPET_FT.models.Recorrido;
import com.unipiloto.APPET_FT.models.RitmoCardiaco;
import com.unipiloto.APPET_FT.services.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/mascotas")
public class MascotaController {
    @Autowired
    private MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

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
    public ResponseEntity<MascotaDTO> obtenerRitmocardiaco(@PathVariable Integer id, @PathVariable Integer estado){
        MascotaDTO ritmoCardiaco = mascotaService.medirRitmoCardiaco(id,estado);
        if(ritmoCardiaco == null){
            return ResponseEntity.badRequest().build();
        }else{
            return  ResponseEntity.ok().body(ritmoCardiaco);
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
    public ResponseEntity<Recorrido> registrarrecorrido(@PathVariable Integer idMascota, @RequestBody Recorrido recorridoL){
        Recorrido recorrido = mascotaService.registrarRecorridos(idMascota, recorridoL);
        if(recorrido != null){
            return ResponseEntity.ok(recorrido);
        }
        return ResponseEntity.badRequest().build();
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

    @GetMapping("/ritmos/{idMascota}/{fecha}")
    public ResponseEntity<List<RitmoCardiaco>> obtenerRitmosPorFecha(@PathVariable int idMascota, @PathVariable Date fecha) {
        List <RitmoCardiaco> ritmos = mascotaService.obtenerRitmosPorFecha(idMascota, fecha);
        if(ritmos.size() > 0) {
            return ResponseEntity.ok(ritmos);
        }
        return ResponseEntity.badRequest().build();
    }
}
