package com.example.saveandserve.demo.repository;

import com.example.saveandserve.demo.entity.Alergenos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlergenosRepository extends JpaRepository<Alergenos, Long> {
    Optional<Alergenos> findByNombre(String nombre);
}
