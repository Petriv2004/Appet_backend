package com.unipiloto.APPET_FT.controllers;

import com.unipiloto.APPET_FT.dtos.AsociarVeterinarioPorCorreoDTO;
import com.unipiloto.APPET_FT.models.Ejercicio;
import com.unipiloto.APPET_FT.models.Mascota;
import com.unipiloto.APPET_FT.models.Propietario;
import com.unipiloto.APPET_FT.repositories.PropietarioRepository;
import com.unipiloto.APPET_FT.services.PropietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/propietarios")
public class PropietarioController {
    @Autowired
    private PropietarioService propietarioService;
    private final PropietarioRepository propietarioRepository;

    public PropietarioController(PropietarioService propietarioService, PropietarioRepository propietarioRepository) {
        this.propietarioService = propietarioService;
        this.propietarioRepository = propietarioRepository;
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
        Propietario propietarioE = propietarioService.autenticar(propietario.getCorreo(), propietario.getContrasena());
        if(propietarioE != null){
            return ResponseEntity.ok().body(propietarioE);
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

    @PostMapping("/asociar-veterinario-por-correo")
    public ResponseEntity<Object> asociarVeterinarioPorCorreo(@RequestBody AsociarVeterinarioPorCorreoDTO dto) {
        Map<String, String> response = new HashMap<>();
        Optional<Propietario> propietarioOpt = propietarioRepository.findByCorreo(dto.getCorreoPropietario());
        Optional<Propietario> veterinarioOpt = propietarioRepository.findByCorreo(dto.getCorreoVeterinario());

        if (propietarioOpt.isPresent() && veterinarioOpt.isPresent()) {
            Propietario propietario = propietarioOpt.get();
            Propietario veterinario = veterinarioOpt.get();

            if (!veterinario.getRol().equals("Veterinario")) {
                response.put("mesaje" , "El usuario con correo " + dto.getCorreoVeterinario() + " no es un veterinario.");
                return ResponseEntity.ok().body(response);
            }

            propietario.getVeterinarios().add(veterinario);
            propietarioRepository.save(propietario);

            response.put("mesaje" , "Veterinario asociado correctamente.");
            return ResponseEntity.ok().body(response);
        }
        response.put("mesaje" , "Propietario o veterinario no encontrado.");
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/asociar-cuidador-por-correo")
    public ResponseEntity<Object> asociarCuidadorioPorCorreo(@RequestBody AsociarVeterinarioPorCorreoDTO dto) {
        Map<String, String> response = new HashMap<>();
        Optional<Propietario> propietarioOpt = propietarioRepository.findByCorreo(dto.getCorreoPropietario());
        Optional<Propietario> cuidadorOpt = propietarioRepository.findByCorreo(dto.getCorreoVeterinario());

        if (propietarioOpt.isPresent() && cuidadorOpt.isPresent()) {
            Propietario propietario = propietarioOpt.get();
            Propietario cuidador = cuidadorOpt.get();

            if (!cuidador.getRol().equals("Cuidador")) {
                response.put("mesaje" , "El usuario con correo " + dto.getCorreoVeterinario() + " no es un cuidador.");
                return ResponseEntity.ok().body(response);
            }
            propietario.getVeterinarios().add(cuidador);
            propietarioRepository.save(propietario);

            response.put("mesaje" , "Cuidador asociado correctamente.");
            return ResponseEntity.ok().body(response);
        }
        response.put("mesaje" , "Propietario o cuidador no encontrado.");
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/mascotas-veterinario/{correoVeterinario}")
    public ResponseEntity<List<Mascota>> obtenerMascotasDeVeterinario(@PathVariable String correoVeterinario){
        List <Mascota> mascotas = propietarioService.obtenerMascotasDeVeterinarios(correoVeterinario);

        if(mascotas != null){
            return ResponseEntity.ok().body(mascotas);
        }
        return ResponseEntity.badRequest().build();
    }
}
