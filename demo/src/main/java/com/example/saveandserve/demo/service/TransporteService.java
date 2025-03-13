package com.example.saveandserve.demo.service;

import com.example.saveandserve.demo.entity.TipoTransporte;
import com.example.saveandserve.demo.entity.Transporte;
import com.example.saveandserve.demo.repository.TransporteRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class TransporteService {

    private final TransporteRepository transporteRepository;

    // Obtener todas las empresas de transporte
    public List<Transporte> obtenerTodos() {
        return transporteRepository.findAll();
    }

    // Buscar empresa por ID
    public Transporte obtenerPorId(Long id) {
        return transporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transporte no encontrado"));
    }

    // Guardar o actualizar una empresa de transporte
    public Transporte guardar(Transporte transporte) {
        return transporteRepository.save(transporte);
    }

    // Eliminar una empresa por ID
    public void eliminar(Long id) {
        transporteRepository.deleteById(id);
    }

    // Buscar empresas por tipo de camión
    public List<Transporte> buscarPorTipoCamion(TipoTransporte tipoCamion) {
        return transporteRepository.findByTipoTransporteContaining(tipoCamion);
    }
}

