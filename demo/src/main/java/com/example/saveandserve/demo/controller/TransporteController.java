package com.example.saveandserve.demo.controller;
import com.example.saveandserve.demo.entity.TipoTransporte;
import com.example.saveandserve.demo.entity.Transporte;
import com.example.saveandserve.demo.service.TransporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/transportes")
@RequiredArgsConstructor
public class TransporteController {

    private final TransporteService transporteService;

    @GetMapping
    public List<Transporte> obtenerTodos() {
        return transporteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transporte> obtenerPorId(@PathVariable Long id) {
        Transporte transporte = transporteService.obtenerPorId(id);
        return ResponseEntity.ok(transporte);
    }

    @PostMapping
    public ResponseEntity<Transporte> guardar(@RequestBody Transporte transporte) {
        Transporte nuevoTransporte = transporteService.guardar(transporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTransporte);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        transporteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipo/{tipoTransporte}")
    public ResponseEntity<List<Transporte>> obtenerPorTipoCamion(@PathVariable TipoTransporte tipoTransporte) {
        List<Transporte> empresas = transporteService.buscarPorTipoCamion(tipoTransporte);
        return ResponseEntity.ok(empresas);
    }

    
}

