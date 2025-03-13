package com.example.saveandserve.demo.service;

import com.example.saveandserve.demo.entity.Articulo;
import com.example.saveandserve.demo.repository.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    public List<Articulo> getAllArticulos() {
        return articuloRepository.findAll();
    }

    public Optional<Articulo> getArticuloById(Long id) {
        return articuloRepository.findById(id);
    }

    public Articulo saveArticulo(Articulo articulo) {
        return articuloRepository.save(articulo);
    }

    public void deleteArticulo(Long id) {
        articuloRepository.deleteById(id);
    }
}
