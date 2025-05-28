package com.example.saveandserve.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.saveandserve.demo.entity.Donacion;
import com.example.saveandserve.demo.entity.Donacion.EstadoEnvio;
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
//Cuidado que este  metodo funcionaba 
    // @PutMapping("/{id}/estado")
    // public ResponseEntity<Donacion> updateEstado(@PathVariable Long id, @RequestBody EstadoRequest request) {
    //     try {
    //         System.out.println("Actualizando donación ID: " + id + " a estado: " + request.estadoEnvio);
            
    //         Optional<Donacion> donacionOpt = donacionService.obtenerPorId(id);
    //         if (donacionOpt.isPresent()) {
    //             Donacion donacion = donacionOpt.get();
                
    //             // Validar que el estado es válido
    //             EstadoEnvio nuevoEstado;
    //             try {
    //                 nuevoEstado = EstadoEnvio.valueOf(request.estadoEnvio);
    //             } catch (IllegalArgumentException e) {
    //                 System.err.println("Estado inválido: " + request.estadoEnvio);
    //                 return ResponseEntity.badRequest().build();
    //             }
                
    //             donacion.setEstadoEnvio(nuevoEstado);
    //             Donacion donacionActualizada = donacionService.registrar(donacion);
                
    //             System.out.println("Donación actualizada correctamente");
    //             return ResponseEntity.ok(donacionActualizada);
    //         } else {
    //             System.err.println("Donación no encontrada con ID: " + id);
    //             return ResponseEntity.notFound().build();
    //         }
    //     } catch (Exception e) {
    //         System.err.println("Error al actualizar donación: " + e.getMessage());
    //         e.printStackTrace();
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    //     }
    // }

    
// @PutMapping("/{id}/estado")
// public ResponseEntity<Donacion> updateEstadoDonacion(
//     @PathVariable Long id, 
//     @RequestBody EstadoRequest request) {
    
//     try {
//         System.out.println("Actualizando donación ID: " + id + " a estado: " + request.estadoEnvio);
        
//         Optional<Donacion> donacionOpt = donacionService.obtenerPorId(id);
//         if (donacionOpt.isPresent()) {
//             Donacion donacion = donacionOpt.get();
            
//             // Validar que el estado actual sea ENVIADO antes de permitir cambios
//             if (!donacion.getEstadoEnvio().equals(EstadoEnvio.ENVIADO)) {
//                 System.err.println("Solo se pueden actualizar donaciones en estado ENVIADO");
//                 return ResponseEntity.badRequest().build();
//             }
            
//             // Validar que el nuevo estado sea válido (ENTREGADO o DENEGADO)
//             EstadoEnvio nuevoEstado;
//             try {
//                 nuevoEstado = EstadoEnvio.valueOf(request.estadoEnvio);
//                 if (!nuevoEstado.equals(EstadoEnvio.ENTREGADO) && 
//                     !nuevoEstado.equals(EstadoEnvio.DENEGADO)) {
//                     System.err.println("Estado no válido: " + request.estadoEnvio);
//                     return ResponseEntity.badRequest().build();
//                 }
//             } catch (IllegalArgumentException e) {
//                 System.err.println("Estado inválido: " + request.estadoEnvio);
//                 return ResponseEntity.badRequest().build();
//             }
            
//             donacion.setEstadoEnvio(nuevoEstado);
//             Donacion donacionActualizada = donacionService.registrar(donacion);
            
//             System.out.println("Donación actualizada correctamente");
//             return ResponseEntity.ok(donacionActualizada);
//         } else {
//             System.err.println("Donación no encontrada con ID: " + id);
//             return ResponseEntity.notFound().build();
//         }
//     } catch (Exception e) {
//         System.err.println("Error al actualizar donación: " + e.getMessage());
//         e.printStackTrace();
//         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//     }
// }

@PutMapping("/{id}/estado")
public ResponseEntity<Donacion> updateEstadoDonacion(
    @PathVariable Long id, 
    @RequestBody EstadoRequest request) {
    
    try {
        System.out.println("Actualizando donación ID: " + id + " a estado: " + request.estadoEnvio);
        
        Optional<Donacion> donacionOpt = donacionService.obtenerPorId(id);
        if (donacionOpt.isPresent()) {
            Donacion donacion = donacionOpt.get();
            EstadoEnvio estadoActual = donacion.getEstadoEnvio();
            
            // Validar que el nuevo estado sea válido
            EstadoEnvio nuevoEstado;
            try {
                nuevoEstado = EstadoEnvio.valueOf(request.estadoEnvio);
            } catch (IllegalArgumentException e) {
                System.err.println("Estado inválido: " + request.estadoEnvio);
                return ResponseEntity.badRequest().build();
            }
            
            // LÓGICA DE VALIDACIÓN MEJORADA
            boolean cambioValido = false;
            
            switch (estadoActual) {
                case PENDIENTE:
                    // Desde PENDIENTE, el admin puede cambiar a ENVIADO o DENEGADO
                    if (nuevoEstado == EstadoEnvio.ENVIADO || nuevoEstado == EstadoEnvio.DENEGADO) {
                        cambioValido = true;
                    }
                    break;
                    
                case ENVIADO:
                    // Desde ENVIADO, el banco puede cambiar a ENTREGADO o DENEGADO
                    if (nuevoEstado == EstadoEnvio.ENTREGADO || nuevoEstado == EstadoEnvio.DENEGADO) {
                        cambioValido = true;
                    }
                    break;
                    
                case ENTREGADO:
                case DENEGADO:
                    // Estados finales - no se pueden cambiar
                    cambioValido = false;
                    break;
            }
            
            if (!cambioValido) {
                System.err.println("Cambio de estado no válido: " + estadoActual + " → " + nuevoEstado);
                return ResponseEntity.badRequest().build();
            }
            
            donacion.setEstadoEnvio(nuevoEstado);
            Donacion donacionActualizada = donacionService.registrar(donacion);
            
            System.out.println("Donación actualizada correctamente: " + estadoActual + " → " + nuevoEstado);
            return ResponseEntity.ok(donacionActualizada);
        } else {
            System.err.println("Donación no encontrada con ID: " + id);
            return ResponseEntity.notFound().build();
        }
    } catch (Exception e) {
        System.err.println("Error al actualizar donación: " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}



    //new : esto lo necesito para poder actualizar el estado de una donación
    public static class EstadoRequest {
        public String estadoEnvio;
        
        // Constructor vacío necesario para Jackson
        public EstadoRequest() {}
        
        public EstadoRequest(String estadoEnvio) {
            this.estadoEnvio = estadoEnvio;
        }
    }

}