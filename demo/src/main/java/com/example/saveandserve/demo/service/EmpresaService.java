// package com.example.saveandserve.demo.service;
// import java.math.BigDecimal;
// import java.util.List;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.example.saveandserve.demo.entity.Donacion;
// import com.example.saveandserve.demo.entity.Empresa;
// import com.example.saveandserve.demo.repository.EmpresaRepository;
// import com.example.saveandserve.demo.repository.DonacionRepository;
// @Service
// public class EmpresaService {

//     @Autowired
//     private EmpresaRepository empresaRepository;
//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     public List<Empresa> obtenerTodas() {
//         return empresaRepository.findAll();
//     }

//     public Optional<Empresa> obtenerPorId(Long id) {
//         return empresaRepository.findById(id);
//     }

//     public Empresa guardar(Empresa empresa) {
//         empresa.setContrasenia(passwordEncoder.encode(empresa.getContrasenia())); // 🔒 Cifrar antes de guardar
//         return empresaRepository.save(empresa);
//     }
//     public Optional<Empresa> actualizar(Long id, Empresa empresaActualizada) {
//         return empresaRepository.findById(id).map(empresaExistente -> {
//             empresaExistente.setNombre(empresaActualizada.getNombre());
//             empresaExistente.setEmail(empresaActualizada.getEmail());
//             empresaExistente.setDireccion(empresaActualizada.getDireccion());
//             empresaExistente.setTelefono(empresaActualizada.getTelefono());
//             empresaExistente.setCif(empresaActualizada.getCif());
//             empresaExistente.setCiudad(empresaActualizada.getCiudad());
//             empresaExistente.setTipo(empresaActualizada.getTipo());
//             empresaExistente.setSuscripcion(empresaActualizada.getSuscripcion());

//             if (empresaActualizada.getContrasenia() != null && !empresaActualizada.getContrasenia().isEmpty())  {
//                 empresaExistente.setContrasenia(passwordEncoder.encode(empresaActualizada.getContrasenia()));
//             }
//             // Si no se proporciona contraseña, mantener la existente
    
//             return empresaRepository.save(empresaExistente);
//         });
//     }

//     public boolean eliminar(Long id) {
//         if (empresaRepository.existsById(id)) {
//             empresaRepository.deleteById(id);
//             return true;
//         }
//         return false;
//     }

//     public boolean autenticar(String email, String password) {
//         return empresaRepository.findAll().stream()
//                 .anyMatch(emp -> emp.getEmail().equals(email) && emp.getContrasenia().equals(password));
//     }

//     public Empresa saveEmpresa(Empresa empresa) {
//         empresa.setContrasenia(passwordEncoder.encode(empresa.getContrasenia())); // 🔒 Encripta la contraseña
//         return empresaRepository.save(empresa);
//     }
    
//     public Empresa loadEmpresaById(Long id) {
//         return empresaRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
//     }

//     public Optional<Empresa> obtenerPorEmail(String email) {
//         return empresaRepository.findByEmail(email);
//     }



//     public Page<Empresa> obtenerEmpresasPaginadas(Pageable pageable) {
//         return empresaRepository.findAll(pageable);
//     }

//     public BigDecimal getTotalDonaciones(Long id) {
//     return empresaRepository.getTotalDonacionesByEmpresaId(id);
// }

// //NEW: Paara obtener empresas validadas
//     public List<Empresa> obtenerEmpresasValidadas() {
//         return empresaRepository.findByDocumentacionValidada(true);
//     }

//     //Tuve que crear esto debido a que  cuando actualizaba el estado me cambiaba la contraseña 
//     public Empresa actualizarSoloValidacion(Long id, boolean documentacionValidada) {
//         return empresaRepository.findById(id).map(empresaExistente -> {
//             // Solo actualizar el campo de validación, NO tocar la contraseña
//             empresaExistente.setDocumentacionValidada(documentacionValidada);
//             return empresaRepository.save(empresaExistente);
//         }).orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
//     }

// //Esto es para calcular el total de donaciones entregadas
// public BigDecimal calcularTotalDonacionesEntregadas(Long empresaId) {
//     List<Donacion> donacionesEntregadas = donacionRepository.findByEmpresaIdAndEstadoEnvio(
//         empresaId, 
//         Donacion.EstadoEnvio.ENTREGADO
//     );
    
//     return donacionesEntregadas.stream()
//         .filter(donacion -> donacion.getTotalDonacion() != null)
//         .map(Donacion::getTotalDonacion)
//         .reduce(BigDecimal.ZERO, BigDecimal::add);
// }


// }

package com.example.saveandserve.demo.service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.saveandserve.demo.entity.Donacion;
import com.example.saveandserve.demo.entity.Empresa;
import com.example.saveandserve.demo.repository.EmpresaRepository;
import com.example.saveandserve.demo.repository.DonacionRepository; // ← AGREGAR ESTE IMPORT

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;
    
    @Autowired
    private DonacionRepository donacionRepository; // ← AGREGAR ESTA INYECCIÓN
    
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

    //NEW: Para obtener empresas validadas
    public List<Empresa> obtenerEmpresasValidadas() {
        return empresaRepository.findByDocumentacionValidada(true);
    }

    //Tuve que crear esto debido a que cuando actualizaba el estado me cambiaba la contraseña 
    public Empresa actualizarSoloValidacion(Long id, boolean documentacionValidada) {
        return empresaRepository.findById(id).map(empresaExistente -> {
            // Solo actualizar el campo de validación, NO tocar la contraseña
            empresaExistente.setDocumentacionValidada(documentacionValidada);
            return empresaRepository.save(empresaExistente);
        }).orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
    }

    //Esto es para calcular el total de donaciones entregadas
    public BigDecimal calcularTotalDonacionesEntregadas(Long empresaId) {
        List<Donacion> donacionesEntregadas = donacionRepository.findByEmpresaIdAndEstadoEnvio(
            empresaId, 
            Donacion.EstadoEnvio.ENTREGADO
        );
        
        return donacionesEntregadas.stream()
            .filter(donacion -> donacion.getTotalDonacion() != null)
            .map(Donacion::getTotalDonacion)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
