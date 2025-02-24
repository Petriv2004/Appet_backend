package com.example.appet.services;

import com.example.appet.models.Propietario;
import com.example.appet.repositories.PropietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropietarioService {
    @Autowired
    private PropietarioRepository propietarioRepository;

    public PropietarioService(PropietarioRepository propietarioRepository) {
        this.propietarioRepository = propietarioRepository;
    }

    public String guardarPropietario(Propietario propietario) {
        Optional<Propietario> propietarioExistente = propietarioRepository.findByCorreo(propietario.getCorreo());

        if (propietarioExistente.isPresent()) {
            return "Error: El propietario con este correo ya existe.";
        }

        propietarioRepository.save(propietario);
        return "Propietario guardado exitosamente.";
    }
}
