// package com.example.saveandserve.demo.service;

// import java.math.BigDecimal;
// import java.util.List;
// import java.util.Optional;

// import org.springframework.stereotype.Service;

// import com.example.saveandserve.demo.entity.Donacion;
// import com.example.saveandserve.demo.entity.LineaProducto;
// import com.example.saveandserve.demo.entity.Producto;
// import com.example.saveandserve.demo.repository.DonacionRepository;
// import com.example.saveandserve.demo.repository.LineaProductoRepository;
// import com.example.saveandserve.demo.repository.ProductoRepository;

// import jakarta.transaction.Transactional;

// @Service
// public class DonacionService {

//     private final DonacionRepository donacionRepository;
//     private final ProductoRepository productoRepository;
//     private final LineaProductoRepository lineaProductoRepository;

//     public DonacionService(DonacionRepository donacionRepository, ProductoRepository productoRepository, LineaProductoRepository lineaProductoRepository) {
//         this.donacionRepository = donacionRepository;
//         this.productoRepository = productoRepository;
//         this.lineaProductoRepository = lineaProductoRepository;
//     }

//     @Transactional
//     public Donacion registrar(Donacion donacion) {
//         BigDecimal totalDonacion = BigDecimal.ZERO;
    
//         for (LineaProducto lp : donacion.getLineasProducto()) {
//             Producto producto = lp.getProducto();
    
//             // Verificar si el producto ya existe
//             Optional<Producto> productoExistente = productoRepository.findByIdProducto(producto.getIdProducto());
    
//             if (productoExistente.isPresent()) {
//                 lp.setProducto(productoExistente.get()); // Usar el existente
//             } else {
//                 producto = productoRepository.save(producto); // Guardar el nuevo producto
//                 lp.setProducto(producto);
//             }
    
//             // Calcular el subtotal
//             lp.setSubtotal(lp.getPrecioUnitario().multiply(BigDecimal.valueOf(lp.getCantidad())));
//             totalDonacion = totalDonacion.add(lp.getSubtotal());
    
//             // **Asignar la donación a la línea de producto**
//             lp.setDonacion(donacion);
//         }
    
//         // Asignar el total de la donación
//         donacion.setTotalDonacion(totalDonacion);
    
//         // **Guardar la donación primero**
//         Donacion nuevaDonacion = donacionRepository.save(donacion);
    
//         // **Guardar las líneas de producto asociadas a la donación**
//         lineaProductoRepository.saveAll(donacion.getLineasProducto());
    
//         return nuevaDonacion;
//     }
    

//     public List<Donacion> obtenerTodas() {
//         return donacionRepository.findAll();
//     }

//     public Optional<Donacion> obtenerPorId(Long id) {
//         return donacionRepository.findById(id);
//     }

//     public Optional<Donacion> actualizar(Long id, Donacion donacionActualizada) {
//         return donacionRepository.findById(id).map(donacionExistente -> {
//             donacionExistente.setTotalDonacion(donacionActualizada.getTotalDonacion());
//             donacionExistente.setFechaEntrega(donacionActualizada.getFechaEntrega());
//             donacionExistente.setEstadoEnvio(donacionActualizada.getEstadoEnvio());
//             donacionExistente.setEmpresa(donacionActualizada.getEmpresa());
//             donacionExistente.setBancoDeAlimentos(donacionActualizada.getBancoDeAlimentos());
//             donacionExistente.setTransporte(donacionActualizada.getTransporte());
//             return donacionRepository.save(donacionExistente);
//         });
//     }

//     public void eliminar(Long id) {
//         donacionRepository.deleteById(id);
//     }

//     public List<Donacion> obtenerDonacionesPorEmpresa(Long empresaId) {
//         return donacionRepository.findByEmpresaId(empresaId);
//     }
// }


//No me maten

package com.example.saveandserve.demo.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.saveandserve.demo.entity.Donacion;
import com.example.saveandserve.demo.entity.LineaProducto;
import com.example.saveandserve.demo.entity.Producto;
import com.example.saveandserve.demo.entity.TipoTransporte;
import com.example.saveandserve.demo.repository.DonacionRepository;
import com.example.saveandserve.demo.repository.LineaProductoRepository;
import com.example.saveandserve.demo.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class DonacionService {
    private static final Logger log = LoggerFactory.getLogger(DonacionService.class);

    private final DonacionRepository donacionRepository;
    private final ProductoRepository productoRepository;
    private final LineaProductoRepository lineaProductoRepository;

    public DonacionService(DonacionRepository donacionRepository, 
                          ProductoRepository productoRepository,
                          LineaProductoRepository lineaProductoRepository) {
        this.donacionRepository = donacionRepository;
        this.productoRepository = productoRepository;
        this.lineaProductoRepository = lineaProductoRepository;
    }

    @Transactional
    public Donacion registrar(Donacion donacion) {
        try {
            BigDecimal totalDonacion = BigDecimal.ZERO;
            
            for (LineaProducto lp : donacion.getLineasProducto()) {
                Producto producto = lp.getProducto();
                
                // Asegurar que tipoTransporte coincida con tipoProducto
                if (producto.getTipoProducto() != null) {
                    producto.setTipoTransporte(TipoTransporte.valueOf(producto.getTipoProducto().name()));
                }

                // Verificar si el producto ya existe
                Optional<Producto> productoExistente = productoRepository.findByIdProducto(producto.getIdProducto());

                if (productoExistente.isPresent()) {
                    log.debug("Producto existente encontrado: {}", productoExistente.get().getIdProducto());
                    lp.setProducto(productoExistente.get());
                } else {
                    log.debug("Creando nuevo producto: {}", producto.getIdProducto());
                    validateProducto(producto);
                    producto = productoRepository.save(producto);
                    lp.setProducto(producto);
                }

                // Calcular el subtotal
                lp.setSubtotal(lp.getPrecioUnitario().multiply(BigDecimal.valueOf(lp.getCantidad())));
                totalDonacion = totalDonacion.add(lp.getSubtotal());

                lp.setDonacion(donacion);
            }

            donacion.setTotalDonacion(totalDonacion);
            
            log.info("Guardando donación con {} líneas de producto", donacion.getLineasProducto().size());
            Donacion nuevaDonacion = donacionRepository.save(donacion);
            lineaProductoRepository.saveAll(donacion.getLineasProducto());

            return nuevaDonacion;
        } catch (Exception e) {
            log.error("Error al registrar donación: ", e);
            throw new RuntimeException("Error al procesar la donación", e);
        }
    }

    private void validateProducto(Producto producto) {
        if (producto.getTipoProducto() == null) {
            throw new IllegalArgumentException("El tipo de producto no puede ser nulo");
        }
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        if (producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio del producto debe ser válido");
        }
    }

    public List<Donacion> obtenerTodas() {
        return donacionRepository.findAll();
    }

    public Optional<Donacion> obtenerPorId(Long id) {
        return donacionRepository.findById(id);
    }

    @Transactional
    public Optional<Donacion> actualizar(Long id, Donacion donacionActualizada) {
        try {
            return donacionRepository.findById(id).map(donacionExistente -> {
                donacionExistente.setTotalDonacion(donacionActualizada.getTotalDonacion());
                donacionExistente.setFechaEntrega(donacionActualizada.getFechaEntrega());
                donacionExistente.setEstadoEnvio(donacionActualizada.getEstadoEnvio());
                donacionExistente.setEmpresa(donacionActualizada.getEmpresa());
                donacionExistente.setBancoDeAlimentos(donacionActualizada.getBancoDeAlimentos());
                donacionExistente.setTransporte(donacionActualizada.getTransporte());
                return donacionRepository.save(donacionExistente);
            });
        } catch (Exception e) {
            log.error("Error al actualizar donación {}: ", id, e);
            throw new RuntimeException("Error al actualizar la donación", e);
        }
    }

    @Transactional
    public void eliminar(Long id) {
        try {
            donacionRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error al eliminar donación {}: ", id, e);
            throw new RuntimeException("Error al eliminar la donación", e);
        }
    }

    // public List<Donacion> obtenerDonacionesPorEmpresa(Long empresaId) {
    //     return donacionRepository.findByEmpresaId(empresaId);
    // }
    public List<Donacion> obtenerPorEmpresaId(Long empresaId) {
        try {
            log.info("Buscando donaciones para empresa ID: {}", empresaId);
            List<Donacion> donaciones = donacionRepository.findByEmpresaId(empresaId);
            log.info("Encontradas {} donaciones", donaciones.size());
            return donaciones;
        } catch (Exception e) {
            log.error("Error al obtener donaciones para empresa {}: ", empresaId, e);
            return Collections.emptyList();
        }
    }
}