package com.example.saveandserve.demo.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GetAdministradorDto {
    private Long id;
    private String nombreUsuario;
    private String nombreCompleto;
    private String email;
    private String avatar;
    private LocalDateTime creadoEn;
    private LocalDateTime lastPasswordChangeAt;
}
