package com.unipiloto.APPET_FT.services;

import com.unipiloto.APPET_FT.models.Historial;
import com.unipiloto.APPET_FT.models.Mascota;
import com.unipiloto.APPET_FT.repositories.HistorialRepository;
import com.unipiloto.APPET_FT.repositories.MascotaRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HistorialService {
    @Autowired
    private HistorialRepository historialRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    public HistorialService(HistorialRepository historialRepository, MascotaRepository mascotaRepository) {
        this.historialRepository = historialRepository;
        this.mascotaRepository = mascotaRepository;
    }

    public Mascota registrarHistorial(Historial historial, Integer idMascota) {
        Optional<Mascota> mascotaOptional = mascotaRepository.findById(idMascota);

        if (mascotaOptional.isPresent()) {
            Mascota mascota = mascotaOptional.get();
            historialRepository.save(historial);
            mascota.setHistorial(historial);
            mascotaRepository.save(mascota);
            return mascotaOptional.get();
        } else {
            return null;
        }
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
            if (datosActualizados.getVacunas() != null) {
                historial.setVacunas(datosActualizados.getVacunas());
            }
            if (datosActualizados.getCirugias() != null) {
                historial.setCirugias(datosActualizados.getCirugias());
            }
            historialRepository.save(historial);
            return mascotaOptional.get();
        } else {
            return null;
        }
    }

    public Historial obtenerHistorial(Integer idMascota) {
        Optional<Mascota> mascotaOptional = mascotaRepository.findById(idMascota);
        if (mascotaOptional.isPresent()) {
            return mascotaOptional.get().getHistorial();
        } else {
            return null;
        }
    }

    @Transactional
    public Historial eliminarHistorial(Integer idMascota) {
        Optional<Mascota> mascotaOpt = mascotaRepository.findById(idMascota);
        if (mascotaOpt.isEmpty()) {
            return null;
        }

        Mascota mascota = mascotaOpt.get();
        Historial historial = mascota.getHistorial();
        if (historial == null) {
            return null;
        }
        mascota.setHistorial(null);
        mascotaRepository.save(mascota);
        historialRepository.delete(historial);

        return historial;
    }

}
