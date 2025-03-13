package com.example.saveandserve.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "donacion") 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDonacion;

    @Column(nullable = true)
    private BigDecimal totalDonacion;

    @Column(nullable = false)
    private LocalDate fechaEntrega;

    public static enum EstadoEnvio {
        ENTREGADO,
        ENVIADO,
        PENDIENTE
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEnvio estadoEnvio;

    // Relación reflexiva 1:N: Una donación puede tener muchas donaciones relacionadas
    @OneToMany(mappedBy = "donacionPrincipal")
    private List<Donacion> donacionesRelacionadas;

    // Relación inversa: Una donación puede estar relacionada con una donación principal
    @ManyToOne
    @JoinColumn(name = "id_donacion_principal") // Columna que almacena la referencia
    private Donacion donacionPrincipal;
    
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    // @JsonManagedReference 
    private Empresa empresa;

    @OneToMany(mappedBy = "donacion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LineaProducto> lineasProducto;

    @ManyToOne
    @JoinColumn(name = "banco_de_alimentos_id", nullable = false)
    private BancoDeAlimentos bancoDeAlimentos;

    @ManyToOne
    @JoinColumn(name = "transporte_id", nullable = false)
    private Transporte transporte;
}
