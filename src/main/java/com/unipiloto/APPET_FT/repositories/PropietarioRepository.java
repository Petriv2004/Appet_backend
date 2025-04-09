package com.unipiloto.APPET_FT.repositories;

import com.unipiloto.APPET_FT.models.Ejercicio;
import com.unipiloto.APPET_FT.models.Mascota;
import com.unipiloto.APPET_FT.models.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PropietarioRepository extends JpaRepository<Propietario, Integer> {
    Optional<Propietario> findByCorreo(String correo);

    List<Propietario> findByVeterinariosContaining(Propietario veterinario);

    @Query("SELECT m FROM Mascota m WHERE m.propietario IN " +
            "(SELECT p FROM Propietario p JOIN p.veterinarios v WHERE v.correo = :correoVeterinario)")
    List<Mascota> findMascotasByVeterinarioCorreo(@Param("correoVeterinario") String correoVeterinario);

    @Query("SELECT e FROM Ejercicio e WHERE e.propietario IN " +
            "(SELECT p FROM Propietario p JOIN p.veterinarios v WHERE v.correo = :correoVeterinario)")
    List<Ejercicio> findEjerciciosByVeterinarioCorreo(@Param("correoVeterinario") String correoVeterinario);

    @Query("SELECT p.nombre FROM Propietario p WHERE p.correo = :correo")
    String findNombreByCorreo(String correo);
}
