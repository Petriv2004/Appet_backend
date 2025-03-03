package com.unipiloto.APPET_FT.services;

import com.unipiloto.APPET_FT.models.Propietario;
import com.unipiloto.APPET_FT.repositories.PropietarioRepository;
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

    public boolean autenticar(String correo, String contrasenaIngresada) {
        Optional<Propietario> propietarioOptional = propietarioRepository.findByCorreo(correo);

        if (propietarioOptional.isPresent()) {
            return propietarioOptional.get().getContrasena().equals(contrasenaIngresada);
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
