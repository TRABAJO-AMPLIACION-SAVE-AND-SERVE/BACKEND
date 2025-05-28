package com.example.saveandserve.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.saveandserve.demo.entity.Donacion;

public interface DonacionRepository extends JpaRepository<Donacion, Long> {

    //List<Donacion> findByEmpresaId(Long empresaId);
      @Query("SELECT d FROM Donacion d WHERE d.empresa.id = :empresaId")
    List<Donacion> findByEmpresaId(@Param("empresaId") Long empresaId);


  // NUEVO: Método para obtener donaciones por empresa y estado
  @Query("SELECT d FROM Donacion d WHERE d.empresa.id = :empresaId AND d.estadoEnvio = :estadoEnvio")
  List<Donacion> findByEmpresaIdAndEstadoEnvio(
      @Param("empresaId") Long empresaId, 
      @Param("estadoEnvio") Donacion.EstadoEnvio estadoEnvio
  );

  }
