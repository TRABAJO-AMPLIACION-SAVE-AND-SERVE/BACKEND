package com.example.saveandserve.demo.service;

import com.example.saveandserve.demo.entity.LineaProducto;
import com.example.saveandserve.demo.entity.Producto;
import com.example.saveandserve.demo.repository.LineaProductoRepository;
import com.example.saveandserve.demo.repository.ProductoRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LineaProductoService {

    private LineaProductoRepository lineaProductoRepository;

    private ProductoRepository productoRepository;

    public LineaProductoService(LineaProductoRepository lineaProductoRepository) {
        this.lineaProductoRepository = lineaProductoRepository;
    }

    public List<LineaProducto> getAllLineasProducto() {
        return lineaProductoRepository.findAll();
    }

    public Optional<LineaProducto> getLineaProductoById(Long id) {
        return lineaProductoRepository.findById(id);
    }

    public List<LineaProducto> getLineasProductoByDonacion(Long donacionId) {
        return lineaProductoRepository.findByDonacion_IdDonacion(donacionId);
    }

    public LineaProducto saveLineaProducto(LineaProducto lineaProducto) {
        return lineaProductoRepository.save(lineaProducto);
    }

    public void deleteLineaProducto(Long id) {
        lineaProductoRepository.deleteById(id);
    }

    
    public Producto verificarOCrearProducto(Producto producto) {
    return productoRepository.findByIdProducto(producto.getIdProducto())
                .orElseGet(() -> productoRepository.save(producto));
    }
}
