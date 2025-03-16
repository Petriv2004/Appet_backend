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
import java.util.Random;

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

    public int medirRitmoCardiaco(int id, int estado) {
        Optional<Mascota> mascotaOpt = mascotaRepository.findById(id);
        if (mascotaOpt.isPresent()) {
            Mascota mascota = mascotaOpt.get();
            String tipo = mascota.getTipo();
            String tamanio = mascota.getTamanio();
            int ritmoCardiaco = 0;
            if (tipo.equals("Perro")) {
                if (tamanio.equals("Grande")) {
                    ritmoCardiaco = (estado == 1) ? generarNumeroRandom(60, 100) : generarNumeroFueraDeRango(60, 100);
                } else if (tamanio.equals("Mediano")) {
                    ritmoCardiaco = (estado == 1) ? generarNumeroRandom(80, 120) : generarNumeroFueraDeRango(80, 120);
                } else {
                    ritmoCardiaco = (estado == 1) ? generarNumeroRandom(100, 160) : generarNumeroFueraDeRango(100, 160);
                }
            } else if (tipo.equals("Gato")) {
                ritmoCardiaco = (estado == 1) ? generarNumeroRandom(140, 220) : generarNumeroFueraDeRango(140, 220);
            }
            mascota.setRitmoCardiaco(ritmoCardiaco);
            mascotaRepository.save(mascota);
            return ritmoCardiaco;
        }

        return -1;
    }

    private int generarNumeroRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    private int generarNumeroFueraDeRango(int minNormal, int maxNormal) {
        Random random = new Random();
        if (random.nextBoolean()) {
            return minNormal - random.nextInt(20) - 1;
        } else {
            return maxNormal + random.nextInt(20) + 1;
        }
    }

}
