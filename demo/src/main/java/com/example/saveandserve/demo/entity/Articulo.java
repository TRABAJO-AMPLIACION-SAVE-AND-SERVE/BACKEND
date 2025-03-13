package com.example.saveandserve.demo.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articulos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Articulo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArticulo;
    
    @Column(nullable = false, length = 200)
    private String titulo;
    
    @Column(length = 300)
    private String subtitulo;
    
    @Column(columnDefinition = "TEXT") 
    private String imagen;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String contenido;
}
