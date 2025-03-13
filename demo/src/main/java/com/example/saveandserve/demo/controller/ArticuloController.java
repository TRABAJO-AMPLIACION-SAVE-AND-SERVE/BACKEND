package com.example.saveandserve.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.saveandserve.demo.entity.Articulo;
import com.example.saveandserve.demo.service.ArticuloService;

@RestController
@RequestMapping("/articulos")
// @CrossOrigin(origins = "http://localhost:4200") 

public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    @GetMapping
    public ResponseEntity<List<Articulo>> getAllArticulos() {
        return ResponseEntity.ok(articuloService.getAllArticulos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Articulo> getArticuloById(@PathVariable Long id) {
        Optional<Articulo> articulo = articuloService.getArticuloById(id);
        return articulo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Articulo> createArticulo(@RequestBody Articulo articulo) {
        return ResponseEntity.ok(articuloService.saveArticulo(articulo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticulo(@PathVariable Long id) {
        articuloService.deleteArticulo(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Articulo> updateArticulo(@PathVariable Long id, @RequestBody Articulo articuloActualizado) {
    Optional<Articulo> articuloOptional = articuloService.getArticuloById(id);
    if (articuloOptional.isPresent()) {
        Articulo articuloExistente = articuloOptional.get();
        articuloExistente.setTitulo(articuloActualizado.getTitulo());
        articuloExistente.setSubtitulo(articuloActualizado.getSubtitulo());
        articuloExistente.setImagen(articuloActualizado.getImagen());
        articuloExistente.setContenido(articuloActualizado.getContenido());

        return ResponseEntity.ok(articuloService.saveArticulo(articuloExistente));
    } else {
        return ResponseEntity.notFound().build();
    }
}

}
