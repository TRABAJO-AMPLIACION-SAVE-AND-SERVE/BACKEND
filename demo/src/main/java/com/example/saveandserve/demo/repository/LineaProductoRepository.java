package com.example.saveandserve.demo.repository;

import com.example.saveandserve.demo.entity.LineaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineaProductoRepository extends JpaRepository<LineaProducto, Long> {
    List<LineaProducto> findByDonacion_IdDonacion(Long donacionId);
}
