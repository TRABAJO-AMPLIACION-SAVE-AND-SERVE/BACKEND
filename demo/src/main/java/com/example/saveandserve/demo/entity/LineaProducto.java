package com.example.saveandserve.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "linea_producto") 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @ManyToOne
    @JoinColumn(name = "donacion_id", nullable = false)
    @JsonIgnore
    private Donacion donacion;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto; 

    @Column(nullable = false)
    private Integer cantidad; 

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario; 

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal; 

    @PrePersist
    public void calcularSubtotal() {
        if (cantidad != null && precioUnitario != null) {
            subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad)); 
        }
    }
}
