package com.example.appet.services;

import com.example.appet.models.Mascota;
import com.example.appet.repositories.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MascotaService {
    @Autowired
    private MascotaRepository mascotaRepository;

    public Mascota registrarMascota(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }
}
