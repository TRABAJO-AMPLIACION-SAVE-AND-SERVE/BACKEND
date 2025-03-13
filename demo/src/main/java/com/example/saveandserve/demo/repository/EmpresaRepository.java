package com.example.saveandserve.demo.repository;
import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.saveandserve.demo.entity.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByEmail(String email);
    Page<Empresa> findAll(Pageable pageable);

@Query("SELECT SUM(d.totalDonacion) FROM Donacion d WHERE d.empresa.id = :empresaId")
    BigDecimal getTotalDonacionesByEmpresaId(@Param("empresaId") Long empresaId);
    
}
