package com.example.saveandserve.demo.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class JwtAdministradorResponse {
    private String token;
    private String nombreUsuario;
    private String avatar;
    private Set<String> roles;
}
