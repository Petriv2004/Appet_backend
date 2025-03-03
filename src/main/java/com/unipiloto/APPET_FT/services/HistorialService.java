package com.unipiloto.APPET_FT.services;

import com.unipiloto.APPET_FT.models.Historial;
import com.unipiloto.APPET_FT.models.Mascota;
import com.unipiloto.APPET_FT.repositories.HistorialRepository;
import com.unipiloto.APPET_FT.repositories.MascotaRepository;
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

    public String registrarHistorial(Historial historial, Integer idMascota) {
        Optional<Mascota> mascotaOptional = mascotaRepository.findById(idMascota);

        if (mascotaOptional.isPresent()) {
            Mascota mascota = mascotaOptional.get();
            historialRepository.save(historial);
            mascota.setHistorial(historial);
            mascotaRepository.save(mascota);
            return "Historial registrado con éxito.";
        } else {
            return "Error: La mascota con ID " + idMascota + " no existe.";
        }
    }
}
