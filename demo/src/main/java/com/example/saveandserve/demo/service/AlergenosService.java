package com.example.saveandserve.demo.service;

import com.example.saveandserve.demo.entity.Alergenos;
import com.example.saveandserve.demo.repository.AlergenosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlergenosService {

    private final AlergenosRepository alergenosRepository;

    public AlergenosService(AlergenosRepository alergenosRepository) {
        this.alergenosRepository = alergenosRepository;
    }

    public List<Alergenos> getAllAlergenos() {
        return alergenosRepository.findAll();
    }

    public Optional<Alergenos> getAlergenoById(Long id) {
        return alergenosRepository.findById(id);
    }

    public Alergenos saveAlergeno(Alergenos alergeno) {
        return alergenosRepository.save(alergeno);
    }

    public void deleteAlergeno(Long id) {
        alergenosRepository.deleteById(id);
    }
}
