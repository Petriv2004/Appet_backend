package com.example.appet.controllers;

import com.example.appet.models.Propietario;
import com.example.appet.services.PropietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/propietarios")
public class PropietarioController {

    @Autowired
    private PropietarioService propietarioService;

    public PropietarioController(PropietarioService propietarioService) {
        this.propietarioService = propietarioService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> guardarPropietario(@RequestBody Propietario propietario) {
        String mensaje = propietarioService.guardarPropietario(propietario);
        if (mensaje.startsWith("Error")) {
            return ResponseEntity.badRequest().body(mensaje);
        }
        return ResponseEntity.ok(mensaje);
    }

    @PostMapping("/login")
    public ResponseEntity<Propietario> login (@RequestBody Propietario propietario){
        if(propietarioService.autenticar(propietario.getCorreo(), propietario.getContrasena())){
            return ResponseEntity.ok().build();
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
}
