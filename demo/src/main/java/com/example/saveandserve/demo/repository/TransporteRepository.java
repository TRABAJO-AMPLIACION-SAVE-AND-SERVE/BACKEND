package com.example.saveandserve.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.saveandserve.demo.entity.TipoTransporte;
import com.example.saveandserve.demo.entity.Transporte;
public interface TransporteRepository extends JpaRepository<Transporte, Long> {
        @Query("SELECT t FROM Transporte t JOIN t.tipoTransporte tt WHERE tt = :tipo")
    List<Transporte> findByTipoTransporte(@Param("tipo") TipoTransporte tipoTransporte);
    List<Transporte> findByTipoTransporteContaining(TipoTransporte tipoTransporte);
}
