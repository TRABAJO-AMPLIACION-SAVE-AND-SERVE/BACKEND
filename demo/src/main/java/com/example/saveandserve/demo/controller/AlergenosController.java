// package com.example.saveandserve.demo.controller;

// import java.util.Optional;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.saveandserve.demo.entity.Alergenos;
// import com.example.saveandserve.demo.service.AlergenosService;

// import lombok.extern.slf4j.Slf4j;
// @Slf4j
// @RestController
// @RequestMapping("alergenos")
// public class AlergenosController {

//     private final AlergenosService alergenosService;

//     public AlergenosController(AlergenosService alergenosService) {
//         this.alergenosService = alergenosService;
//     }

//     // @GetMapping
//     // public List<Alergenos> getAllAlergenos() {
//     //     return alergenosService.getAllAlergenos();
//     // }

//     @GetMapping("/{id}")
//     public ResponseEntity<Alergenos> getAlergenoById(@PathVariable Long id) {
//         Optional<Alergenos> alergeno = alergenosService.getAlergenoById(id);
//         return alergeno.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//     }

//     @PostMapping
//     public Alergenos saveAlergeno(@RequestBody Alergenos alergeno) {
//         return alergenosService.saveAlergeno(alergeno);
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteAlergeno(@PathVariable Long id) {
//         alergenosService.deleteAlergeno(id);
//         return ResponseEntity.noContent().build();
//     }

//     // @GetMapping
//     // public ResponseEntity<List<Alergenos>> getAllAlergenos() {
//     //     try {
//     //         List<Alergenos> alergenos = alergenosService.getAllAlergenos();
//     //         return ResponseEntity.ok(alergenos);
//     //     } catch (Exception e) {
//     //         log.error("Error al obtener alérgenos: ", e);
//     //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//     //     }
//     // }
    
// }


package com.example.saveandserve.demo.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.saveandserve.demo.entity.Alergenos;
import com.example.saveandserve.demo.service.AlergenosService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController

@RequestMapping("/alergenos")
public class AlergenosController {

    private final AlergenosService alergenosService;

    public AlergenosController(AlergenosService alergenosService) {
        this.alergenosService = alergenosService;
    }

//     @GetMapping
// public ResponseEntity<List<Alergenos>> getAllAlergenos() {
//     try {
//         List<Alergenos> alergenos = alergenosService.getAllAlergenos();
//         if (alergenos.isEmpty()) {
//             return ResponseEntity.noContent().build();
//         }
//         return ResponseEntity.ok(alergenos);
//     } catch (Exception e) {
//         log.error("Error al obtener alérgenos: ", e);
//         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                            .body(Collections.emptyList());
//     }
// }
@GetMapping
public ResponseEntity<?> getAllAlergenos() {
    try {
        List<Alergenos> alergenos = alergenosService.getAllAlergenos();
        log.info("Recuperados {} alérgenos", alergenos.size());
        return ResponseEntity.ok(alergenos);
    } catch (Exception e) {
        log.error("Error al obtener alérgenos", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Collections.emptyList());
    }
}

    @GetMapping("/{id}")
    public ResponseEntity<Alergenos> getAlergenoById(@PathVariable Long id) {
        try {
            Optional<Alergenos> alergeno = alergenosService.getAlergenoById(id);
            if (alergeno.isPresent()) {
                log.info("Alérgeno encontrado con ID: {}", id);
                return ResponseEntity.ok(alergeno.get());
            } else {
                log.warn("No se encontró alérgeno con ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error al obtener alérgeno con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Alergenos> saveAlergeno(@RequestBody Alergenos alergeno) {
        try {
            Alergenos savedAlergeno = alergenosService.saveAlergeno(alergeno);
            log.info("Alérgeno guardado correctamente con ID: {}", savedAlergeno.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAlergeno);
        } catch (Exception e) {
            log.error("Error al guardar alérgeno: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlergeno(@PathVariable Long id) {
        try {
            alergenosService.deleteAlergeno(id);
            log.info("Alérgeno eliminado correctamente con ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error al eliminar alérgeno con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}