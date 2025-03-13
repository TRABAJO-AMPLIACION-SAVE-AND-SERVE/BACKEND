package com.example.saveandserve.demo.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transporte")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombreTransporte;

    @ElementCollection(targetClass = TipoTransporte.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "transporte_tipos", joinColumns = @JoinColumn(name = "transporte_id"))
    @Enumerated(EnumType.STRING) // Usa STRING en lugar de ORDINAL
    @Column(name = "tipo_transporte")
    private Set<TipoTransporte> tipoTransporte = new HashSet<>();
}
