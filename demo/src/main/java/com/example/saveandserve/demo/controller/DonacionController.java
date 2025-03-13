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

import com.example.saveandserve.demo.entity.Donacion;
import com.example.saveandserve.demo.service.DonacionService;

@RestController
@RequestMapping("/donaciones")
public class DonacionController {

    @Autowired
    private DonacionService donacionService;

    @GetMapping
    public ResponseEntity<List<Donacion>> obtenerTodas() {
        List<Donacion> donaciones = donacionService.obtenerTodas();
        return donaciones.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(donaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donacion> obtenerPorId(@PathVariable Long id) {
        Optional<Donacion> donacion = donacionService.obtenerPorId(id);
        return donacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Donacion> registrar(@RequestBody Donacion donacion) {
        Donacion nuevaDonacion = donacionService.registrar(donacion);
        return ResponseEntity.ok(nuevaDonacion);
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<Donacion>> obtenerPorEmpresa(@PathVariable Long empresaId) {
        List<Donacion> donaciones = donacionService.obtenerPorEmpresaId(empresaId);
        return donaciones.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(donaciones);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Donacion> actualizar(@PathVariable Long id, @RequestBody Donacion donacionActualizada) {
        Optional<Donacion> donacion = donacionService.actualizar(id, donacionActualizada);
        return donacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        donacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}