package com.unipiloto.APPET_FT.services;

import com.unipiloto.APPET_FT.models.Ejercicio;
import com.unipiloto.APPET_FT.models.Historial;
import com.unipiloto.APPET_FT.models.Mascota;
import com.unipiloto.APPET_FT.models.Propietario;
import com.unipiloto.APPET_FT.repositories.HistorialRepository;
import com.unipiloto.APPET_FT.repositories.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {
    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired
    private HistorialRepository historialRepository;

    public MascotaService(MascotaRepository mascotaRepository, HistorialRepository historialRepository) {
        this.mascotaRepository = mascotaRepository;
        this.historialRepository = historialRepository;
    }

    public Mascota registrarMascota(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    public Mascota actualizarHistorialPorMascota(Integer idMascota, Historial datosActualizados) {
        Optional<Mascota> mascotaOptional = mascotaRepository.findById(idMascota);
        if (mascotaOptional.isPresent()) {
            Mascota mascota = mascotaOptional.get();
            Historial historial = mascota.getHistorial();
            if (historial == null) {
                return null;
            }
            if (datosActualizados.getEnfermedades() != null) {
                historial.setEnfermedades(datosActualizados.getEnfermedades());
            }
            if (datosActualizados.getMedicinas() != null) {
                historial.setMedicinas(datosActualizados.getMedicinas());
            }
            if (datosActualizados.getSangre() != null) {
                historial.setSangre(datosActualizados.getSangre());
            }
            if (datosActualizados.getPeso() != 0) {
                historial.setPeso(datosActualizados.getPeso());
            }
            if (datosActualizados.getFoto() != null) {
                historial.setFoto(datosActualizados.getFoto());
            }
            if(datosActualizados.getVacunas() != null){
                historial.setVacunas(datosActualizados.getVacunas());
            }
            if(datosActualizados.getCirugias() != null){
                historial.setCirugias(datosActualizados.getCirugias());
            }
            historialRepository.save(historial);
            return mascotaOptional.get();
        } else {
            return null;
        }
    }

    public Mascota obtenerMascota(int id){
        Optional<Mascota> mascota = mascotaRepository.findById(id);

        if(mascota.isPresent()){
            return mascota.get();
        }
        return null;
    }

}
