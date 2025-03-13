package com.example.saveandserve.demo.controller;

import com.example.saveandserve.demo.dto.GetUserDto;
import com.example.saveandserve.demo.dto.converter.UsuarioDtoConverter;
import com.example.saveandserve.demo.entity.BancoDeAlimentos;
import com.example.saveandserve.demo.entity.Empresa;
import com.example.saveandserve.demo.entity.Usuario;
import com.example.saveandserve.demo.repository.BancoDeAlimentosRepository;
import com.example.saveandserve.demo.repository.EmpresaRepository;
import com.example.saveandserve.demo.repository.UsuarioRepository;
import com.example.saveandserve.demo.security.jwt.JwtProvider;
import com.example.saveandserve.demo.security.jwt.entidades.JwtUserResponse;
import com.example.saveandserve.demo.security.jwt.entidades.LoginRequest;
import org.springframework.security.crypto.password.PasswordEncoder;  // Importar PasswordEncoder

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.BadCredentialsException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AutenticacionController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider tokenProvider;
    private final UsuarioDtoConverter converter;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final BancoDeAlimentosRepository bancoDeAlimentosRepository;
    private final PasswordEncoder passwordEncoder;  // Añadir PasswordEncoder como dependencia

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication =
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()
                        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails principal = (UserDetails) authentication.getPrincipal();

        char tipoUsuario = obtenerTipoUsuario(authentication);

        switch (tipoUsuario) {
            case 'E' -> {
                Empresa empresa = empresaRepository.findByEmail(principal.getUsername()).orElseThrow();
                String token = tokenProvider.generateToken(principal.getUsername(), tipoUsuario);
                return ResponseEntity.status(HttpStatus.CREATED)
				.body(convertUserEntityAndTokenToJwtEmpresaResponse(empresa, token));

            }
            case 'U' -> {
                Usuario usuario = usuarioRepository.findByEmail(principal.getUsername()).orElseThrow();
                String token = tokenProvider.generateToken(principal.getUsername(), tipoUsuario);
                return ResponseEntity.status(HttpStatus.CREATED)
				.body(convertUserEntityAndTokenToJwtUserResponse(usuario, token));
            }
            case 'D' -> {
                BancoDeAlimentos bancoDeAlimentos = bancoDeAlimentosRepository.findByEmail(principal.getUsername()).orElseThrow();
                String token = tokenProvider.generateToken(principal.getUsername(), tipoUsuario);
                return ResponseEntity.status(HttpStatus.CREATED)
				.body(convertUserEntityAndTokenToJwtBancoDeAlimentosResponse(bancoDeAlimentos, token));
            }
            default -> throw new IllegalStateException("Tipo de usuario desconocido: " + tipoUsuario);
        }        
    }

    private JwtUserResponse convertUserEntityAndTokenToJwtUserResponse(Usuario usuario, String jwtToken) {

        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");
        
		return JwtUserResponse.jwtUserResponseBuilder()
						.username(usuario.getUsername())
                        .email(usuario.getEmail())
						.roles(roles)
						.token(jwtToken)
						.build();
	}

    private JwtUserResponse convertUserEntityAndTokenToJwtEmpresaResponse(Empresa empresa, String jwtToken) {
        Set<String> roles = new HashSet<>();
        roles.add("EMPRESA");
		return JwtUserResponse.jwtUserResponseBuilder()
						.username(empresa.getUsername())
                        .email(empresa.getEmail())
						.roles(roles)
						.token(jwtToken)
						.build();
	}
    private JwtUserResponse convertUserEntityAndTokenToJwtBancoDeAlimentosResponse(BancoDeAlimentos bancoDeAlimentos, String jwtToken) {
        Set<String> roles = new HashSet<>();
        roles.add("BANCO_DE_ALIMENTOS");
		return JwtUserResponse.jwtUserResponseBuilder()
						.username(bancoDeAlimentos.getUsername())
                        .email(bancoDeAlimentos.getEmail())
						.roles(roles)
						.token(jwtToken)
						.build();
	}
    // private ResponseEntity<JwtUserResponse> autenticarUsuario(Object entidad, String rol) {
    //     String email;
    //     String password;

    //     if (entidad instanceof Usuario usuario) {
    //         email = usuario.getEmail();
    //         password = usuario.getPassword();
    //     } else if (entidad instanceof Empresa empresa) {
    //         email = empresa.getEmail();
    //         password = empresa.getContrasenia();
    //     } else if (entidad instanceof BancoDeAlimentos banco) {
    //         email = banco.getEmail();
    //         password = banco.getContrasenia();
    //     } else {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    //     }

    //     Authentication authentication = authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(email, password)
    //     );

    //     SecurityContextHolder.getContext().setAuthentication(authentication);
    //     String jwtToken = tokenProvider.generateToken(authentication);

    //     JwtUserResponse respuesta = JwtUserResponse.jwtUserResponseBuilder()
    //             .username(email)
    //             .email(email)
    //             .roles(Set.of(rol))
    //             .token(jwtToken)
    //             .build();

    //     return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    // }

    @GetMapping("/user/me")
    public GetUserDto me(@AuthenticationPrincipal Usuario user) {
        return converter.convertUserEntityToGetUserDto(user);
    }

    public char obtenerTipoUsuario(Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_EMPRESA"))) {
            return 'E'; // Empresa
        } else if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            return 'U'; // Usuario
        } else if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_BANCO_DE_ALIMENTOS"))) {
            return 'D'; // Banco de Alimentos
        }
        throw new IllegalArgumentException("El usuario no tiene un rol válido.");
    }
    
}
