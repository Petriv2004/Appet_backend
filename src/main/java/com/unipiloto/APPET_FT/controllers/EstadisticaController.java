package com.unipiloto.APPET_FT.controllers;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unipiloto.APPET_FT.models.Mascota;
import com.unipiloto.APPET_FT.services.EstadisticaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticaController {

    @Autowired
    private EstadisticaService estadisticaService;

    public EstadisticaController(EstadisticaService estadisticaService) {
        this.estadisticaService = estadisticaService;
    }

    @GetMapping("/promedio-ritmoCardiaco/{idMascota}/{fecha}")
    public ResponseEntity<Map<String, Double>> promedioRitmocardiacoPorFecha(@PathVariable Integer idMascota,
            @PathVariable Date fecha) {
        Map<String, Double> respuesta = estadisticaService.obtenerPromedioRitmoCardiacoPorFechaAndMascota(fecha,
                idMascota);
        if (respuesta != null) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/ritmoCardiacoMinimo/{idMascota}")
    public ResponseEntity<Map<String, Integer>> ritmoCardiacoMinimo(@PathVariable Integer idMascota) {
        Map<String, Integer> respuesta = estadisticaService.obtenerPromedioRitmoCardiacoMinimo(idMascota);
        if (respuesta != null) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/ritmoCardiacoMaximo/{idMascota}")
    public ResponseEntity<Map<String, Integer>> ritmoCardiacoMaximo(@PathVariable Integer idMascota) {
        Map<String, Integer> respuesta = estadisticaService.obtenerPromedioRitmoCardiacoMaximo(idMascota);
        if (respuesta != null) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/total-recorridos/{idMascota}/{mes}/{anio}")
    public ResponseEntity<Map<String, Long>> recorridoMesAnio(@PathVariable Integer idMascota,
            @PathVariable Integer mes, @PathVariable Integer anio) {
        Map<String, Long> respuesta = estadisticaService.obtenerCantidadRecorridosUnicosPorMes(idMascota, mes, anio);
        if (respuesta != null) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/recorridoKm/{idMascota}/{fecha}")
    public ResponseEntity<Map<String, Double>> recorridoKm(@PathVariable Integer idMascota, @PathVariable Date fecha) {
        double kilometros = estadisticaService.obtenerTotalKmRecorridos(idMascota, fecha);
        if (kilometros != 0.0) {
            Map<String, Double> respuesta = new HashMap<>();
            respuesta.put("kilometros", kilometros);
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/mascota-sedentaria/{correo}/{mes}/{anio}")
    public ResponseEntity<Mascota> mascotaSedentaria(@PathVariable String correo, @PathVariable Integer mes,
            @PathVariable Integer anio) {
        Mascota respuesta = estadisticaService.obtenerMascotaMasSedentaria(correo, mes, anio);
        if (respuesta != null) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/mascota-activa/{correo}/{mes}/{anio}")
    public ResponseEntity<Mascota> mascotaActiva(@PathVariable String correo, @PathVariable Integer mes,
            @PathVariable Integer anio) {
        Mascota respuesta = estadisticaService.obtenerMascotaMasActiva(correo, mes, anio);
        if (respuesta != null) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.badRequest().build();

    }

    @GetMapping("/visitas-veterinario/{idMascota}")
    public ResponseEntity<Map<String, Long>> visitasVeterinario(@PathVariable Integer idMascota) {
        Map<String, Long> respuesta = estadisticaService.contarVisitasVeterinario(idMascota);
        if (respuesta != null) {
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.badRequest().build();
    }

}