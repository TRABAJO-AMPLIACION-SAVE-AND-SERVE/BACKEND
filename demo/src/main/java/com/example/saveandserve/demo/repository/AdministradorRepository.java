package com.example.saveandserve.demo.repository;

import com.example.saveandserve.demo.entity.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Optional<Administrador> findByNombreUsuario(String nombreUsuario);
}
