package com.unipiloto.APPET_FT.services;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unipiloto.APPET_FT.models.Mascota;
import com.unipiloto.APPET_FT.models.Recorrido;
import com.unipiloto.APPET_FT.repositories.AgendaRepository;
import com.unipiloto.APPET_FT.repositories.MascotaRepository;
import com.unipiloto.APPET_FT.repositories.RecorridoRepository;
import com.unipiloto.APPET_FT.repositories.RitmoCardiacoRepository;

@Service
public class EstadisticaService {
    @Autowired
    private RitmoCardiacoRepository ritmoCardiacoRepository;
    @Autowired
    private RecorridoRepository recorridoRepository;
    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired
    private AgendaRepository agendaRepository;

    public EstadisticaService(RitmoCardiacoRepository ritmoCardiacoRepository, RecorridoRepository recorridoRepository,
            MascotaRepository mascotaRepository, AgendaRepository agendaRepository) {
        this.ritmoCardiacoRepository = ritmoCardiacoRepository;
        this.recorridoRepository = recorridoRepository;
        this.mascotaRepository = mascotaRepository;
        this.agendaRepository = agendaRepository;
    }

    public Map<String, Double> obtenerPromedioRitmoCardiacoPorFechaAndMascota(Date fecha, Integer mascotaId) {
        Double promedio = ritmoCardiacoRepository.getPromedioRitmoCardiacoPorFechaAndMascota(fecha, mascotaId);
        if (promedio != null) {
            Map<String, Double> respuesta = new HashMap<>();
            respuesta.put("Promedio", promedio);
            return respuesta;
        }
        return null;
    }

    public Map<String, Integer> obtenerPromedioRitmoCardiacoMinimo(Integer mascotaId) {
        Integer minimo = ritmoCardiacoRepository.findMinRitmoCardiacoByMascota(mascotaId);
        if (minimo != null) {
            Map<String, Integer> respuesta = new HashMap<>();
            respuesta.put("RitmoCardiacoMinimo", minimo);
            return respuesta;
        }
        return null;
    }

    public Map<String, Integer> obtenerPromedioRitmoCardiacoMaximo(Integer mascotaId) {
        Integer minimo = ritmoCardiacoRepository.findMaxRitmoCardiacoByMascota(mascotaId);
        if (minimo != null) {
            Map<String, Integer> respuesta = new HashMap<>();
            respuesta.put("RitmoCardiacoMaximo", minimo);
            return respuesta;
        }
        return null;
    }

    /**
     * Retorna el número de fechas distintas en las que se realizó un recorrido para
     * una mascota
     * en un mes y año determinados.
     *
     * @param mascotaId El id de la mascota.
     * @param mes       El mes (número: 1-12).
     * @param anio      El año (por ejemplo, 2025).
     * @return La cantidad de días en los que se registró al menos un recorrido.
     */
    public Map<String, Long> obtenerCantidadRecorridosUnicosPorMes(Integer mascotaId, int mes, int anio) {
        Long total = recorridoRepository.countDistinctRecorridosByMascotaAndMonth(mascotaId, mes, anio);

        if (total != null) {
            Map<String, Long> respuesta = new HashMap<>();
            respuesta.put("Total", total);
            return respuesta;
        }
        return null;
    }
    /**
     * Retorna la mascota más sedentaria de un propietario en un mes y año
     * específicos.
     * Se considera la cantidad de días distintos en que se registró algún
     * recorrido.
     *
     * @param correo Propietario del dueño.
     * @param mes    Mes (1-12).
     * @param anio   Año (ej. 2025).
     * @return La mascota con menos días de recorridos (la más sedentaria).
     */
    public Mascota obtenerMascotaMasSedentaria(String correo, int mes, int anio) {
        List<Mascota> mascotas = mascotaRepository.findMascotasMasSedentarias(correo, mes, anio);
        if (mascotas != null && !mascotas.isEmpty()) {
            return mascotas.get(0);
        }
        return null;
    }

    public Mascota obtenerMascotaMasActiva(String correo, int mes, int anio) {
        List<Mascota> mascotas = mascotaRepository.findMascotasMasActivas(correo, mes, anio);
        if (mascotas != null && !mascotas.isEmpty()) {
            return mascotas.get(0);
        }
        return null;
    }

    public double obtenerTotalKmRecorridos(Integer mascotaId, Date fecha) {
        List<Recorrido> recorridos = recorridoRepository.findRecorridosByMascotaAndFecha(mascotaId, fecha);
        if (recorridos == null || recorridos.isEmpty()) {
            return -1.0;
        }
        double totalKm = 0.0;
        Recorrido prev = null;
        for (Recorrido actual : recorridos) {
            if (prev != null) {
                double lat1 = Double.parseDouble(prev.getLatitud());
                double lon1 = Double.parseDouble(prev.getLongitud());
                double lat2 = Double.parseDouble(actual.getLatitud());
                double lon2 = Double.parseDouble(actual.getLongitud());
                totalKm += calcularDistanciaHaversine(lat1, lon1, lat2, lon2);
            }
            prev = actual;
        }
        return totalKm;
    }

    private double calcularDistanciaHaversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public Map<String, Long> contarVisitasVeterinario(Integer mascotaId) {
        Long visitas = agendaRepository.countVisitasVeterinarioByMascota(mascotaId);
        if (visitas != null) {
            Map<String, Long> respuesta = new HashMap<>();
            respuesta.put("Visitas", visitas);
            return respuesta;
        }
        return null;
    }
}