package com.unipiloto.APPET_FT.controllers;

import com.unipiloto.APPET_FT.models.Ejercicio;
import com.unipiloto.APPET_FT.models.Propietario;
import com.unipiloto.APPET_FT.services.PropietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/propietarios")
public class PropietarioController {
    @Autowired
    private PropietarioService propietarioService;

    public PropietarioController(PropietarioService propietarioService) {
        this.propietarioService = propietarioService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<Object> guardarPropietario(@RequestBody Propietario propietario) {
        String mensaje = propietarioService.guardarPropietario(propietario);

        if (mensaje.startsWith("Error")) {
            return ResponseEntity.badRequest().body(mensaje);
        }
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", mensaje);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Propietario> login (@RequestBody Propietario propietario){
        if(propietarioService.autenticar(propietario.getCorreo(), propietario.getContrasena())){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/registrar_ejercicio")
    public ResponseEntity<Ejercicio> guardarEjercicio(@RequestBody Ejercicio ejer){
        Ejercicio ejercicio = propietarioService.guardarEjercicio(ejer);

        if(ejercicio != null){
            return ResponseEntity.ok().body(ejercicio);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/obtener_propietario/{correo}")
    public ResponseEntity<Propietario> obtenerPropietario(@PathVariable String correo){
        Propietario obt = propietarioService.obtenerPropietario(correo);
        if(obt != null){
            return ResponseEntity.ok().body(obt);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/obtenerId/{correo}")
    public ResponseEntity<Integer> obtenerIdPorCorreo(@PathVariable String correo) {
        Propietario propietario = propietarioService.obtenerPropietario(correo);
        if (propietario != null) {
            return ResponseEntity.ok(propietario.getId_propietario());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(-1);
        }
    }

    @GetMapping("/obtenerEjercicios/{correo}")
    public ResponseEntity<List<Ejercicio>> obtenerEjercicios(@PathVariable String correo){
        List <Ejercicio> ejercicios = propietarioService.obtenerEjercicios(correo);
        if(ejercicios == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(ejercicios);
    }

    @GetMapping("/obtenerEjerciciosCanino/{correo}")
    public ResponseEntity<List<Ejercicio>> obtenerEjerciciosCanino(@PathVariable String correo){
        List <Ejercicio> ejercicios = propietarioService.obtenerEjerciciosCanino(correo);
        if(ejercicios == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(ejercicios);
    }

    @GetMapping("/obtenerEjerciciosFelino/{correo}")
    public ResponseEntity<List<Ejercicio>> obtenerEjerciciosFelino(@PathVariable String correo){
        List <Ejercicio> ejercicios = propietarioService.obtenerEjerciciosFelino(correo);
        if(ejercicios == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(ejercicios);
    }

    @GetMapping("/obtenerEjercicio/{idEjercicio}")
    public ResponseEntity<Ejercicio> obtenerEjercicio(@PathVariable Integer idEjercicio){
        Ejercicio ejercicio = propietarioService.obtenerEjercicio(idEjercicio);

        if(ejercicio != null){
            return ResponseEntity.ok().body(ejercicio);
        }
        return  ResponseEntity.badRequest().build();
    }

    @PutMapping("/actualizar_ejercicio/{idEjercicio}")
    public ResponseEntity<Ejercicio> actualizarEjercicio(@PathVariable Integer idEjercicio, @RequestBody Ejercicio ejercicio){
        Ejercicio respuesta = propietarioService.actualizarEjercicio(idEjercicio, ejercicio);
        if(respuesta == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(respuesta);
    }

    @DeleteMapping("/eliminarEjercicio/{correo}/{idAgenda}")
    public ResponseEntity<Ejercicio> eliminarAgenda(@PathVariable String correo, @PathVariable Integer idAgenda) {
        Ejercicio mensaje = propietarioService.eliminarEjercicio(correo,idAgenda);

        if (mensaje == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mensaje);
    }
}
