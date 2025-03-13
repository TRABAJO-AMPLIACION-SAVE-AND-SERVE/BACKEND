package com.example.saveandserve.demo.service;

import com.example.saveandserve.demo.entity.Producto;
import com.example.saveandserve.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Optional<Producto> obtenerPorIdProducto(String idProducto) {
        return productoRepository.findByIdProducto(idProducto);
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public Optional<Producto> actualizar(Long id, Producto productoActualizado) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(productoActualizado.getNombre());
            producto.setIdProducto(productoActualizado.getIdProducto());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setTipoProducto(productoActualizado.getTipoProducto());
            return productoRepository.save(producto);
        });
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    
}
