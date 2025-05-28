package com.example.saveandserve.demo.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.saveandserve.demo.entity.BancoDeAlimentos;

import jakarta.transaction.Transactional;

public interface BancoDeAlimentosRepository extends JpaRepository<BancoDeAlimentos, Long> {
     Optional<BancoDeAlimentos> findByEmail(String email);
     //New: Filtro para obtener bancis de alimentos validados
     List<BancoDeAlimentos> findByDocumentacionValidada(boolean documentacionValidada);
     @Modifying
     @Transactional
@Query("UPDATE BancoDeAlimentos b SET b.documentacionValidada = :validated WHERE b.id = :id")
void updateValidationStatus(@Param("id") Long id, @Param("validated") boolean validated);

}
