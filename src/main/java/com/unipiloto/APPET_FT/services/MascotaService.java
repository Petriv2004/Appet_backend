package com.unipiloto.APPET_FT.services;

import com.unipiloto.APPET_FT.models.Historial;
import com.unipiloto.APPET_FT.models.Mascota;
import com.unipiloto.APPET_FT.repositories.HistorialRepository;
import com.unipiloto.APPET_FT.repositories.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String actualizarHistorialPorMascota(Integer idMascota, Historial datosActualizados) {
        Optional<Mascota> mascotaOptional = mascotaRepository.findById(idMascota);
        if (mascotaOptional.isPresent()) {
            Mascota mascota = mascotaOptional.get();
            Historial historial = mascota.getHistorial();
            if (historial == null) {
                return "Error: La mascota no tiene historial médico.";
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
            historialRepository.save(historial);
            return "Historial actualizado correctamente.";
        } else {
            return "Error: Mascota no encontrada.";
        }
    }
}
