package com.example.saveandserve.demo.service;

import com.example.saveandserve.demo.entity.BancoDeAlimentos;
import com.example.saveandserve.demo.error.RecursoNoEncontradoException;
import com.example.saveandserve.demo.error.SolicitudIncorrectaException;
import com.example.saveandserve.demo.repository.BancoDeAlimentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BancoDeAlimentosService {

    @Autowired
    private BancoDeAlimentosRepository bancoDeAlimentosRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    public List<BancoDeAlimentos> obtenerTodos() {
        if (bancoDeAlimentosRepository.findAll().isEmpty()) {
            throw new RecursoNoEncontradoException("No hay bancos de alimentos registrados.");
        }
        return bancoDeAlimentosRepository.findAll();
    }

    public Optional<BancoDeAlimentos> obtenerPorId(Long id) {
        if (bancoDeAlimentosRepository.findById(id) == null) {
            throw new RecursoNoEncontradoException("Banco de alimentos no encontrado");
        }
        return bancoDeAlimentosRepository.findById(id);
    }

    public BancoDeAlimentos registrar(BancoDeAlimentos banco) {
        if (bancoDeAlimentosRepository.findByEmail(banco.getEmail()).isPresent()) {
            throw new SolicitudIncorrectaException("El email ya está registrado. Intente con otro.");
        }       
        return saveBanco(banco);
    }

    public Optional<BancoDeAlimentos> actualizar(Long id, BancoDeAlimentos bancoActualizado) {
        return bancoDeAlimentosRepository.findById(id).map(bancoExistente -> {
            bancoExistente.setNombre(bancoActualizado.getNombre());
            bancoExistente.setDireccion(bancoActualizado.getDireccion());
            bancoExistente.setTelefono(bancoActualizado.getTelefono());
            bancoExistente.setEmail(bancoActualizado.getEmail());
            bancoExistente.setCiudad(bancoActualizado.getCiudad());

            if (bancoActualizado.getContrasenia() != null && !bancoActualizado.getContrasenia().isEmpty()) {
                bancoExistente.setContrasenia(passwordEncoder.encode(bancoActualizado.getContrasenia()));
            }

            return bancoDeAlimentosRepository.save(bancoExistente);
        });
    }

    public void eliminar(Long id) {
        bancoDeAlimentosRepository.deleteById(id);
    }

    public BancoDeAlimentos saveBanco(BancoDeAlimentos banco) {
        banco.setContrasenia(passwordEncoder.encode(banco.getContrasenia())); // 🔒 Encripta la contraseña
        return bancoDeAlimentosRepository.save(banco);
    }
    
    public BancoDeAlimentos loadBancoById(Long id) {
        return bancoDeAlimentosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banco de alimentos no encontrado"));
    }

    public Page<BancoDeAlimentos> obtenerBancosPaginados(Pageable pageable) {
        return bancoDeAlimentosRepository.findAll(pageable);
    }

    // public Page<BancoDeAlimentos> obtenerBancosPaginados(Pageable pageable) {
    //     return bancoDeAlimentosRepository.findAll(pageable);
    // }
    

    public Optional<BancoDeAlimentos> obtenerPorEmail(String email) { 
        return bancoDeAlimentosRepository.findByEmail(email);
    }
    
}
