package com.example.saveandserve.demo.service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.saveandserve.demo.entity.Empresa;
import com.example.saveandserve.demo.repository.EmpresaRepository;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Empresa> obtenerTodas() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> obtenerPorId(Long id) {
        return empresaRepository.findById(id);
    }

    public Empresa guardar(Empresa empresa) {
        empresa.setContrasenia(passwordEncoder.encode(empresa.getContrasenia())); // 🔒 Cifrar antes de guardar
        return empresaRepository.save(empresa);
    }

    // public Optional<Empresa> actualizar(Long id, Empresa empresaActualizada) {
    //     return empresaRepository.findById(id).map(empresaExistente -> {
    //         empresaExistente.setNombre(empresaActualizada.getNombre());
    //         empresaExistente.setEmail(empresaActualizada.getEmail());
    //         empresaExistente.setDireccion(empresaActualizada.getDireccion());
    //         empresaExistente.setTelefono(empresaActualizada.getTelefono());
    //         empresaExistente.setCif(empresaActualizada.getCif());
    //         empresaExistente.setCiudad(empresaActualizada.getCiudad());
    //         empresaExistente.setSuscripcion(empresaActualizada.getSuscripcion());

    //         if (!passwordEncoder.matches(empresaActualizada.getContrasenia(), empresaExistente.getContrasenia())) {
    //             empresaExistente.setContrasenia(passwordEncoder.encode(empresaActualizada.getContrasenia()));
    //         }

    //         return empresaRepository.save(empresaExistente);
    //     });
    // }

    public Optional<Empresa> actualizar(Long id, Empresa empresaActualizada) {
        return empresaRepository.findById(id).map(empresaExistente -> {
            empresaExistente.setNombre(empresaActualizada.getNombre());
            empresaExistente.setEmail(empresaActualizada.getEmail());
            empresaExistente.setDireccion(empresaActualizada.getDireccion());
            empresaExistente.setTelefono(empresaActualizada.getTelefono());
            empresaExistente.setCif(empresaActualizada.getCif());
            empresaExistente.setCiudad(empresaActualizada.getCiudad());
            empresaExistente.setTipo(empresaActualizada.getTipo());
            empresaExistente.setSuscripcion(empresaActualizada.getSuscripcion());

            if (empresaActualizada.getContrasenia() != null && !empresaActualizada.getContrasenia().isEmpty())  {
                empresaExistente.setContrasenia(passwordEncoder.encode(empresaActualizada.getContrasenia()));
            }
            // Si no se proporciona contraseña, mantener la existente
    
            return empresaRepository.save(empresaExistente);
        });
    }

    public boolean eliminar(Long id) {
        if (empresaRepository.existsById(id)) {
            empresaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean autenticar(String email, String password) {
        return empresaRepository.findAll().stream()
                .anyMatch(emp -> emp.getEmail().equals(email) && emp.getContrasenia().equals(password));
    }

    public Empresa saveEmpresa(Empresa empresa) {
        empresa.setContrasenia(passwordEncoder.encode(empresa.getContrasenia())); // 🔒 Encripta la contraseña
        return empresaRepository.save(empresa);
    }
    
    public Empresa loadEmpresaById(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
    }

    public Optional<Empresa> obtenerPorEmail(String email) {
        return empresaRepository.findByEmail(email);
    }



    public Page<Empresa> obtenerEmpresasPaginadas(Pageable pageable) {
        return empresaRepository.findAll(pageable);
    }

    public BigDecimal getTotalDonaciones(Long id) {
    return empresaRepository.getTotalDonacionesByEmpresaId(id);
}
}
