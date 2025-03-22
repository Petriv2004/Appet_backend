package com.unipiloto.APPET_FT.controllers;

import com.unipiloto.APPET_FT.dtos.CorreoDTO;
import com.unipiloto.APPET_FT.services.EmailService;
import com.unipiloto.APPET_FT.services.MascotaService;
import com.unipiloto.APPET_FT.services.PropietarioService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private MascotaService mascotaService;
    @Autowired
    private PropietarioService propietarioService;

    public EmailController(EmailService emailService, MascotaService mascotaService, PropietarioService propietarioService) {
        this.emailService = emailService;
        this.mascotaService = mascotaService;
        this.propietarioService = propietarioService;
    }

    @PostMapping("/mensaje-cardiaco/{idMascota}/{correo}")
    public ResponseEntity<Object> enviarCorreo(@RequestBody CorreoDTO correoDTO, @PathVariable Integer idMascota, @PathVariable String correo) {
        Map<String, String> response = new HashMap<>();

        String nombreVeterinario = propietarioService.obtenerNombrePorCorreo(correo);
        String correoPropietario = mascotaService.obtenerCorreoPropietario(idMascota);

        if (correoPropietario == null) {
            response.put("mensaje", "No se encontró el propietario de la mascota.");
            return ResponseEntity.badRequest().body(response);
        }
        correoDTO.setMensaje("Le escribe " + nombreVeterinario + ", su veterinario\n" + correoDTO.getMensaje());
        response.put("mensaje", "Correo enviado exitosamente.");
        emailService.enviarCorreo(correoPropietario, correoDTO.getAsunto(), correoDTO.getMensaje());
        return ResponseEntity.ok(response);
    }
}
