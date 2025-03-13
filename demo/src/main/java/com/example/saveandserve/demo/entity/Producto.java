package com.example.saveandserve.demo.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos") 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String idProducto;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio; 

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransporte tipoTransporte = TipoTransporte.SECO; //Valor por defecto

    public enum TipoProducto {
        SECO,
        REFRIGERADO,
        CONGELADO
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoProducto tipoProducto; 

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<LineaProducto> lineaProducto;

    @ManyToMany
    @JoinTable(
        name = "producto_alergeno",
        joinColumns = @JoinColumn(name = "producto_id"),
        inverseJoinColumns = @JoinColumn(name = "alergeno_id")
    )
    private List<Alergenos> alergenos;
    // private Set<Alergenos> alergenos;

//Cosas pa probar
// Método para sincronizar tipoTransporte con tipoProducto
    @PrePersist
    @PreUpdate
    public void sincronizarTipoTransporte() {
        if (this.tipoProducto != null) {
            this.tipoTransporte = TipoTransporte.valueOf(this.tipoProducto.name());
        }
    }

    // Método helper para establecer el tipo de producto y transporte juntos
    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
        if (tipoProducto != null) {
            this.tipoTransporte = TipoTransporte.valueOf(tipoProducto.name());
        }
    }

    // Override del setter de tipoTransporte para evitar valores nulos
    public void setTipoTransporte(TipoTransporte tipoTransporte) {
        this.tipoTransporte = tipoTransporte != null ? tipoTransporte : TipoTransporte.SECO;
    }

}
