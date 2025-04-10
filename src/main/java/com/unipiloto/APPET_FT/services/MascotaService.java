package com.unipiloto.APPET_FT.services;

import com.unipiloto.APPET_FT.dtos.MascotaDTO;
import com.unipiloto.APPET_FT.models.*;
import com.unipiloto.APPET_FT.repositories.HistorialRepository;
import com.unipiloto.APPET_FT.repositories.MascotaRepository;
import com.unipiloto.APPET_FT.repositories.RecorridoRepository;
import com.unipiloto.APPET_FT.repositories.RitmoCardiacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
public class MascotaService {
    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired
    private HistorialRepository historialRepository;
    @Autowired
    private RecorridoRepository recorridoRepository;
    @Autowired
    private RitmoCardiacoRepository ritmoCardiacoRepository;

    public MascotaService(MascotaRepository mascotaRepository, HistorialRepository historialRepository, RecorridoRepository recorridoRepository, RitmoCardiacoRepository ritmoCardiacoRepository) {
        this.mascotaRepository = mascotaRepository;
        this.historialRepository = historialRepository;
        this.recorridoRepository = recorridoRepository;
        this.ritmoCardiacoRepository = ritmoCardiacoRepository;
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

    public List<RitmoCardiaco> obtenerRitmosPorFecha(int idMascota, Date fecha) {
        return ritmoCardiacoRepository.findByMascotaIdAndFecha(idMascota, fecha);
    }

    public MascotaDTO medirRitmoCardiaco(int id, int estado) {
        Optional<Mascota> mascotaOpt = mascotaRepository.findById(id);
        if (mascotaOpt.isPresent()) {
            Mascota mascota = mascotaOpt.get();
            String tipo = mascota.getTipo();
            String tamanio = mascota.getTamanio();
            int ritmoCardiaco = 0;
            if (tipo.equals("Canino")) {
                if (tamanio.equals("Grande")) {
                    ritmoCardiaco = (estado == 1) ? generarNumeroRandom(60, 100) : generarNumeroFueraDeRango(60, 100);
                } else if (tamanio.equals("Mediano")) {
                    ritmoCardiaco = (estado == 1) ? generarNumeroRandom(80, 120) : generarNumeroFueraDeRango(80, 120);
                } else {
                    ritmoCardiaco = (estado == 1) ? generarNumeroRandom(100, 160) : generarNumeroFueraDeRango(100, 160);
                }
            } else if (tipo.equals("Felino")) {
                ritmoCardiaco = (estado == 1) ? generarNumeroRandom(140, 220) : generarNumeroFueraDeRango(140, 220);
            }
            Date fechaActual = Date.valueOf(LocalDate.now());
            Time horaActual = Time.valueOf(LocalTime.now());

            RitmoCardiaco ritmo = new RitmoCardiaco();
            ritmo.setMascota(mascota);
            ritmo.setRitmoCardiaco(ritmoCardiaco);
            ritmo.setFecha(fechaActual);
            ritmo.setHora(horaActual);
            ritmoCardiacoRepository.save(ritmo);

            MascotaDTO mascotaDTO= new MascotaDTO(tipo, tamanio, ritmoCardiaco);
            return mascotaDTO;
        }
        return null;
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

    public String obtenerCorreoPropietario(Integer idMascota) {
        return mascotaRepository.findCorreoByIdMascota(idMascota);
    }

    public List<Recorrido> recorridoPorFecha(Integer id_Mascota, Date fecha){
        Optional<Mascota> mascota = mascotaRepository.findById(id_Mascota);
        if(mascota.isPresent()){
            List<Recorrido> recorridosExistentes = recorridoRepository.findByMascotaAndFecha(id_Mascota, fecha);
            if (!recorridosExistentes.isEmpty()) {
                return recorridosExistentes;
            }
            Random random = new Random();
            if(random.nextBoolean()){
                int saberRecorrido = random.nextInt(5) + 1;
                ArrayList<Recorrido> recorrido = (ArrayList<Recorrido>) recorridosPredeterminados(saberRecorrido);
                for(Recorrido r : recorrido){
                    r.setFecha(fecha);
                    r.setMascota(mascota.get());
                }
                recorridoRepository.saveAll(recorrido);
                return recorrido;
            }
            return null;
        }
        return null;
    }

    private List<Recorrido> recorridosPredeterminados(int recorrido){
        List <Recorrido> recorrido1 = new ArrayList<>();
        switch (recorrido){
            case 1:
                recorrido1.add(new Recorrido("4.728827732343978", "-74.2593390495657", "Permitido"));
                recorrido1.add(new Recorrido("4.726785497118797", "-74.26074452706382", "Permitido"));
                recorrido1.add(new Recorrido("4.723834403076459", "-74.26054067914971", "Permitido"));
                recorrido1.add(new Recorrido("4.723556400820856", "-74.25879187890393", "Permitido"));
                recorrido1.add(new Recorrido("4.7262722642574575", "-74.256420806132", "Permitido"));
                recorrido1.add(new Recorrido("4.728827732343978", "-74.2593390495657", "Permitido"));
                break;
            case 2:
                recorrido1.add(new Recorrido("4.677107731196722", "-74.04828082004924", "Permitido"));
                recorrido1.add(new Recorrido("4.675988677126647", "-74.04330412026795", "Permitido"));
                recorrido1.add(new Recorrido("4.672722238806989", "-74.04260616846936", "No Permitido"));
                recorrido1.add(new Recorrido("4.674778886931795", "-74.04597454454084", "Permitido"));
                recorrido1.add(new Recorrido("4.677107731196722", "-74.04828082004924", "Permitido"));
                break;
            case 3:
                recorrido1.add(new Recorrido("4.664444406124218", "-74.09330668311696", "Permitido"));
                recorrido1.add(new Recorrido("4.668593395197645", "-74.10051646081733", "Permitido"));
                recorrido1.add(new Recorrido("4.6714591774492344", "-74.10476507981933", "Permitido"));
                recorrido1.add(new Recorrido("4.67026412332741", "-74.11156207355992", "Permitido"));
                recorrido1.add(new Recorrido("4.665174135128044", "-74.10864383020501", "Permitido"));
                recorrido1.add(new Recorrido("4.657731564815394", "-74.10031825331232", "Permitido"));
                recorrido1.add(new Recorrido("4.664444406124218", "-74.09330668311696", "Permitido"));
                break;
            case 4:
                recorrido1.add(new Recorrido("4.6331051876201625", "-74.06562161376029", "Permitido"));
                recorrido1.add(new Recorrido("4.634275161407909", "-74.06618300130985", "Permitido"));
                recorrido1.add(new Recorrido("4.636615103173164", "-74.06562161376029", "Permitido"));
                recorrido1.add(new Recorrido("4.636030118458289", "-74.06087533720483", "No Permitido"));
                recorrido1.add(new Recorrido("4.63193521189631", "-74.0617174185292", " No Permitido"));
                recorrido1.add(new Recorrido("4.629569820281749", "-74.06526436713783", "Permitido"));
                recorrido1.add(new Recorrido("4.6331051876201625", "-74.06562161376029", "Permitido"));
                break;
            case 5:
                recorrido1.add(new Recorrido("4.659523882250615", "-74.1255873896177", "Permitido"));
                recorrido1.add(new Recorrido("4.655658012954027", "-74.12181078246606", "Permitido"));
                recorrido1.add(new Recorrido("4.650927380837307", "-74.1263529180944", "Permitido"));
                recorrido1.add(new Recorrido("4.648027945444981", "-74.1343144142519", "Permitido"));
                recorrido1.add(new Recorrido("4.656370148368423", "-74.13834619756244", "Permitido"));
                recorrido1.add(new Recorrido("4.659523882250615", "-74.1255873896177", "Permitido"));
                break;
        }
        return recorrido1;
    }

    public Recorrido registrarRecorridos(Integer idMascota, Recorrido recorrido) {
        Optional<Mascota> mascotaOpt = mascotaRepository.findById(idMascota);
        if (mascotaOpt.isEmpty()) {
            return null;
        }
        java.util.Date fechaOriginal = recorrido.getFecha();
        Instant instant = fechaOriginal.toInstant();
        LocalDate fechaLocal = instant.atZone(ZoneId.of("UTC")).toLocalDate();
        java.sql.Date fechaSql = java.sql.Date.valueOf(fechaLocal);

        Mascota mascota = mascotaOpt.get();
            recorrido.setMascota(mascota);
            recorrido.setFecha(fechaSql);
        recorridoRepository.save(recorrido);
        return recorrido;
    }

    public Mascota actualizarUbicacion(Integer idMascota) {
        Optional<Mascota> mascotaOpt = mascotaRepository.findById(idMascota);

        if (mascotaOpt.isPresent()) {
            Mascota mascota = mascotaOpt.get();
            Date hoy = Date.valueOf(LocalDate.now());
            List<Recorrido> recorridosExistentes = recorridoRepository.findByMascotaAndFecha(idMascota, hoy);

            Double ultimaLatitud = Double.parseDouble(recorridosExistentes.get(recorridosExistentes.size()-1).getLatitud());
            Double ultimaLongitud = Double.parseDouble(recorridosExistentes.get(recorridosExistentes.size()-1).getLongitud());

            mascota.setLatitud(ultimaLatitud);
            mascota.setLongitud(ultimaLongitud);
            mascotaRepository.save(mascota);

            return mascota;
        }
        return null;
    }

    public Mascota eliminarMascota(Integer idMascota){
        Optional <Mascota> mascota = mascotaRepository.findById(idMascota);
        if(mascota.isPresent()){
            mascotaRepository.deleteById(idMascota);
            return mascota.get();
        }
        return null;
    }
}
