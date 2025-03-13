package com.example.saveandserve.demo.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.saveandserve.demo.entity.Empresa;
import com.example.saveandserve.demo.service.EmpresaService;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<List<Empresa>> obtenerEmpresas() {
        List<Empresa> empresas = empresaService.obtenerTodas();
        return empresas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obtenerEmpresaPorId(@PathVariable Long id) {
        Optional<Empresa> empresa = empresaService.obtenerPorId(id);
        return empresa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/registro")
    public ResponseEntity<Empresa> registrar(@RequestBody Empresa empresa) {
        Empresa nuevaEmpresa = empresaService.guardar(empresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEmpresa);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> actualizar(@PathVariable Long id, @RequestBody Empresa empresa) {
        Optional<Empresa> empresaActualizada = empresaService.actualizar(id, empresa);
        return empresaActualizada.map(ResponseEntity::ok)
                                 .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id) {
        boolean eliminado = empresaService.eliminar(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Empresa> obtenerEmpresaPorEmail(@PathVariable String email) {
        Optional<Empresa> empresa = empresaService.obtenerPorEmail(email);
        return empresa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    

    @GetMapping("/paginadas")
    public ResponseEntity<Page<Empresa>> obtenerEmpresasPaginadas(
          @PageableDefault(page = 0, size = 9) Pageable pageable)
    {
        Page<Empresa> empresas = empresaService.obtenerEmpresasPaginadas(pageable);
        return ResponseEntity.ok(empresas);
    }


    @GetMapping("/{id}/total-donaciones")
    public ResponseEntity<BigDecimal> getTotalDonaciones(@PathVariable Long id) {
        BigDecimal total = empresaService.getTotalDonaciones(id);
        return ResponseEntity.ok(total != null ? total : BigDecimal.ZERO);
    }
}
