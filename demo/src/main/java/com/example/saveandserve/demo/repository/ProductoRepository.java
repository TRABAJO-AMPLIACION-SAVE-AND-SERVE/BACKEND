package com.example.saveandserve.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.saveandserve.demo.entity.Producto;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByIdProducto(String idProducto);
    Optional<Producto> findByNombre(String nombre);
}
