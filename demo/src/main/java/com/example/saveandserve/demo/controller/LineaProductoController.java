package com.example.saveandserve.demo.controller;

import com.example.saveandserve.demo.entity.LineaProducto;
import com.example.saveandserve.demo.service.LineaProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lineas-producto")
public class LineaProductoController {

    private final LineaProductoService lineaProductoService;

    public LineaProductoController(LineaProductoService lineaProductoService) {
        this.lineaProductoService = lineaProductoService;
    }

    @GetMapping
    public List<LineaProducto> getAllLineasProducto() {
        return lineaProductoService.getAllLineasProducto();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LineaProducto> getLineaProductoById(@PathVariable Long id) {
        Optional<LineaProducto> lineaProducto = lineaProductoService.getLineaProductoById(id);
        return lineaProducto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/donacion/{donacionId}")
    public List<LineaProducto> getLineasProductoByDonacion(@PathVariable Long donacionId) {
        return lineaProductoService.getLineasProductoByDonacion(donacionId);
    }

    @PostMapping
    public ResponseEntity<LineaProducto> saveLineaProducto(@RequestBody LineaProducto lineaProducto) {
        // Verifica si el producto existe o lo crea si no existe
        lineaProducto.setProducto(lineaProductoService.verificarOCrearProducto(lineaProducto.getProducto()));
    
        // Guarda la línea de producto con el producto asegurado en la base de datos
        LineaProducto nuevaLineaProducto = lineaProductoService.saveLineaProducto(lineaProducto);
        return ResponseEntity.ok(nuevaLineaProducto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLineaProducto(@PathVariable Long id) {
        lineaProductoService.deleteLineaProducto(id);
        return ResponseEntity.noContent().build();
    }
}
