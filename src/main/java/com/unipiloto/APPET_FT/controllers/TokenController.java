package com.unipiloto.APPET_FT.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unipiloto.APPET_FT.models.Propietario;
import com.unipiloto.APPET_FT.services.EmailService;
import com.unipiloto.APPET_FT.services.PropietarioService;
import com.unipiloto.APPET_FT.services.TokenService;

@RestController
@RequestMapping("/token")
public class TokenController {
    private TokenService tokenService;
    private EmailService emailService;
    private PropietarioService propietarioService;

    public TokenController(TokenService tokenService, EmailService emailService,
            PropietarioService propietarioService) {
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.propietarioService = propietarioService;
    }

    @GetMapping("/obtener-token/{correo}")
    public ResponseEntity<Map<String, String>> generacionToken(@PathVariable String correo) {
        Propietario propietario = propietarioService.obtenerPropietario(correo);
        if (propietario != null) {
            String token = tokenService.generarToken(correo);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("token", token);
            String mensajeCorreo = "Hola, " + propietario.getNombre() + ".\n\n"
                    + "Has solicitado un token de verificación. Por favor, utiliza el siguiente para continuar con el proceso:\n\n"
                    + token + "\n\n"
                    + "Este token es válido por 5 minutos. Si no has solicitado un token de verificación, ignora este mensaje.\n\n"
                    + "Saludos,\n"
                    + "El equipo de APPET.";
            emailService.enviarCorreo(correo, "Token de verificación", mensajeCorreo);
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/verify-token/{token}/{correo}")
    public ResponseEntity<Map<String, String>> verifyToken(@PathVariable String token, @PathVariable String correo) {
        boolean isValid = tokenService.verificarToken(token, correo);
        if (!isValid) {
            return ResponseEntity.badRequest().build();
        }
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("respuesta", "Token Validado Correctamente");
        return ResponseEntity.ok(respuesta);
    }
}
