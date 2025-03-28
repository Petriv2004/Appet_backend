package com.unipiloto.APPET_FT.services;

import com.unipiloto.APPET_FT.models.Ejercicio;
import com.unipiloto.APPET_FT.models.Historial;
import com.unipiloto.APPET_FT.models.Mascota;
import com.unipiloto.APPET_FT.models.Propietario;
import com.unipiloto.APPET_FT.repositories.EjercicioRepository;
import com.unipiloto.APPET_FT.repositories.PropietarioRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PropietarioService {
    @Autowired
    private PropietarioRepository propietarioRepository;
    @Autowired
    private EjercicioRepository ejercicioRepository;

    public PropietarioService(PropietarioRepository propietarioRepository, EjercicioRepository ejercicioRepository) {
        this.propietarioRepository = propietarioRepository;
        this.ejercicioRepository = ejercicioRepository;
    }

    public String guardarPropietario(Propietario propietario) {
        Optional<Propietario> propietarioExistente = propietarioRepository.findByCorreo(propietario.getCorreo());
        if (propietarioExistente.isPresent()) {
            return "Error: El propietario con este correo ya existe.";
        }
        String hashedPassword = BCrypt.hashpw(propietario.getContrasena(), BCrypt.gensalt(12));
        propietario.setContrasena(hashedPassword);

        propietarioRepository.save(propietario);
        return "Propietario guardado exitosamente.";
    }

    public Propietario autenticar(String correo, String contrasenaIngresada) {
        Optional<Propietario> propietarioOptional = propietarioRepository.findByCorreo(correo);

        if (propietarioOptional.isPresent()) {
            Propietario propietario = propietarioOptional.get();
            if(BCrypt.checkpw(contrasenaIngresada, propietario.getContrasena())){
                return propietario;
            }
        }
        return null;
    }

    public Propietario obtenerPropietario(String correo){
        Optional<Propietario> propietario = propietarioRepository.findByCorreo(correo);
        if(propietario.isPresent()){
            return propietario.get();
        }
        return null;
    }

    public Ejercicio guardarEjercicio(Ejercicio ejercicio){
        return ejercicioRepository.save(ejercicio);
    }

    public Ejercicio actualizarEjercicio(Integer idEjercicio, Ejercicio datosActualizados) {
        Optional<Ejercicio> ejercicioOptional = ejercicioRepository.findById(idEjercicio);
        if (ejercicioOptional.isPresent()) {
            Ejercicio ejercicio = ejercicioOptional.get();
            if (datosActualizados.getNombre() != null) {
                ejercicio.setNombre(datosActualizados.getNombre());
            }
            if (datosActualizados.getDuracion() != 0) {
                ejercicio.setDuracion(datosActualizados.getDuracion());
            }
            if (datosActualizados.getIntensidad() != null) {
                ejercicio.setIntensidad(datosActualizados.getIntensidad());
            }
            if (datosActualizados.getTiempo_descanso() != 0) {
                ejercicio.setTiempo_descanso(datosActualizados.getTiempo_descanso());
            }
            if (datosActualizados.getImagen() != null) {
                ejercicio.setImagen(datosActualizados.getImagen());
            }
            if(datosActualizados.getEspecie() != null){
                ejercicio.setEspecie(datosActualizados.getEspecie());
            }
            ejercicioRepository.save(ejercicio);
            return ejercicio;
        } else {
            return null;
        }
    }

    public Ejercicio obtenerEjercicio(Integer id){
        Optional<Ejercicio> ejercicioEncontrado = ejercicioRepository.findById(id);

        if(ejercicioEncontrado.isPresent()){
            return ejercicioEncontrado.get();
        }
        return null;
    }

    public List<Ejercicio> obtenerEjercicios(String correo){
        Optional<Propietario> propietario = propietarioRepository.findByCorreo(correo);
        if (propietario.isPresent()){
            return propietario.get().getEjercicioslist();
        }
        return null;
    }

    public List<Ejercicio> obtenerEjerciciosCanino(String correo){
        Optional<Propietario> propietario = propietarioRepository.findByCorreo(correo);
        if (propietario.isPresent()){
            List <Ejercicio> caninoEj = new ArrayList<>();
            for(Ejercicio e : propietario.get().getEjercicioslist()){
                if(e.getEspecie().equals("Canino")){
                    caninoEj.add(e);
                }
            }
            return caninoEj;
        }
        return null;
    }

    public List<Ejercicio> obtenerEjerciciosFelino(String correo){
        Optional<Propietario> propietario = propietarioRepository.findByCorreo(correo);
        if (propietario.isPresent()){
            List <Ejercicio> felinoEj = new ArrayList<>();
            for(Ejercicio e : propietario.get().getEjercicioslist()){
                if(e.getEspecie().equals("Felino")){
                    felinoEj.add(e);
                }
            }
            return felinoEj;
        }
        return null;
    }

    public List<Ejercicio> obtenerEjerciciosDeVeterinario(String correoVeterinario) {
        Optional<Propietario> veterinario = propietarioRepository.findByCorreo(correoVeterinario);
        if (veterinario.isPresent()) {
            List<Ejercicio> ejercicios = propietarioRepository.findEjerciciosByVeterinarioCorreo(correoVeterinario);
            return ejercicios;
        }
        return null;
    }

    public List<Ejercicio> obtenerEjerciciosCaninoVeterinario(String correoVeterinario){
        Optional<Propietario> veterinario = propietarioRepository.findByCorreo(correoVeterinario);
        if (veterinario.isPresent()){
            List <Ejercicio> caninoEj = propietarioRepository.findEjerciciosByVeterinarioCorreo(correoVeterinario);
            ArrayList<Ejercicio> ejercicios = new ArrayList<>();
            for(Ejercicio e : caninoEj){
                if(e.getEspecie().equals("Canino")){
                    ejercicios.add(e);
                }
            }
            return ejercicios;
        }
        return null;
    }

    public List<Ejercicio> obtenerEjerciciosFelinoVeterinario(String correoVeterinario){
        Optional<Propietario> veterinario = propietarioRepository.findByCorreo(correoVeterinario);
        if (veterinario.isPresent()){
            List <Ejercicio> felinoEj = propietarioRepository.findEjerciciosByVeterinarioCorreo(correoVeterinario);
            ArrayList<Ejercicio> ejercicios = new ArrayList<>();
            for(Ejercicio e : felinoEj){
                if(e.getEspecie().equals("Felino")){
                    ejercicios.add(e);
                }
            }
            return ejercicios;
        }
        return null;
    }

    public Ejercicio eliminarEjercicio(String correo, Integer idEjercicio){
        Optional<Propietario> propietarioOpt = propietarioRepository.findByCorreo(correo);
        if(propietarioOpt.isPresent()){
            Optional <Ejercicio> ejercicioOpt = ejercicioRepository.findById(idEjercicio);
            if(ejercicioOpt.isPresent()){
                ejercicioRepository.deleteById(idEjercicio);
                return ejercicioOpt.get();
            }
        }
        return null;
    }

    public List<Mascota> obtenerMascotasDeVeterinarios(String correoVeterinario){
        List<Mascota> mascotas = propietarioRepository.findMascotasByVeterinarioCorreo(correoVeterinario);
        if(mascotas != null){
            return mascotas;
        }
        return null;
    }

    public String obtenerNombrePorCorreo(String correo) {
        return propietarioRepository.findNombreByCorreo(correo);
    }
}
