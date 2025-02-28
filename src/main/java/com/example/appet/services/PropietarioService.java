package com.example.appet.services;

import com.example.appet.models.Propietario;
import com.example.appet.repositories.PropietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropietarioService {
    @Autowired
    private PropietarioRepository propietarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public PropietarioService(PropietarioRepository propietarioRepository) {
        this.propietarioRepository = propietarioRepository;
    }

    public String guardarPropietario(Propietario propietario) {
        Optional<Propietario> propietarioExistente = propietarioRepository.findByCorreo(propietario.getCorreo());
        if (propietarioExistente.isPresent()) {
            return "Error: El propietario con este correo ya existe.";
        }
        propietario.setContrasena(passwordEncoder.encode(propietario.getContrasena()));
        propietarioRepository.save(propietario);
        return "Propietario guardado exitosamente.";
    }

    public boolean autenticar(String correo, String contrasenaIngresada) {
        Optional<Propietario> propietarioOptional = propietarioRepository.findByCorreo(correo);

        if (propietarioOptional.isPresent()) {
            Propietario propietario = propietarioOptional.get();
            return passwordEncoder.matches(contrasenaIngresada, propietario.getContrasena());
        }
        return false;
    }

    public Propietario obtenerPropietario(String correo){
        Optional<Propietario> propietario = propietarioRepository.findByCorreo(correo);
        if(propietario.isPresent()){
            return propietario.get();
        }
        return null;
    }
}
