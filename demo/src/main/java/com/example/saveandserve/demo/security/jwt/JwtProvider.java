package com.example.saveandserve.demo.security.jwt;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.saveandserve.demo.entity.BancoDeAlimentos;
import com.example.saveandserve.demo.entity.Empresa;
import com.example.saveandserve.demo.entity.Usuario;
import com.example.saveandserve.demo.repository.BancoDeAlimentosRepository;
import com.example.saveandserve.demo.repository.EmpresaRepository;
import com.example.saveandserve.demo.repository.UsuarioRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.java.Log;

@Log
@Component
public class JwtProvider {
	
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_TYPE = "JWT ";
	
	// Cuanto más largo mejor, si es alieatorio mejor
	private String jwtSecreto = "IESDoctorBalmis-2DAW-DesarrolloWebEntornoServidor";
	
	private int jwtDurationTokenEnSegundos = 864000;

	@Autowired
	EmpresaRepository empresaRepository;

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired	
	BancoDeAlimentosRepository bancoDeAlimentosRepository;

	public String generateToken(String email, char tipoUsuario) {

		if (tipoUsuario == 'U') {
			Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();
			Date tokenExpirationDate = new Date(System.currentTimeMillis() + jwtDurationTokenEnSegundos * 1000);
			return Jwts.builder()
					.header().add("typ", TOKEN_TYPE).and()
					.subject(Long.toString(usuario.getId()))
					.issuedAt(new Date())
					.expiration(tokenExpirationDate)
					.claim("email", email)
					.claim("fullname", usuario.getUsername())
					// Esto develve los roles en String. Ejemplo [ADMIN, USER] --> "ADMIN, USER"
					.claim("roles", "ADMIN"
						)
					//Crea la clave secreta utilizando el algoritmo HMAC-SHA que se utilizará para firmar el token JWT
					.signWith(Keys.hmacShaKeyFor(jwtSecreto.getBytes())) 
					.compact();

		} else if (tipoUsuario == 'E') {

			Empresa empresa = empresaRepository.findByEmail(email).orElseThrow();
			Date tokenExpirationDate = new Date(System.currentTimeMillis() + jwtDurationTokenEnSegundos * 1000);
			return Jwts.builder()
					.header().add("typ", TOKEN_TYPE).and()
					.subject(Long.toString(empresa.getId()))
					.issuedAt(new Date())
					.expiration(tokenExpirationDate)
					.claim("email", email)
					.claim("fullname", empresa.getUsername())
					// Esto develve los roles en String. Ejemplo [ADMIN, USER] --> "ADMIN, USER"
					.claim("roles", "EMPRESA"
						)
					//Crea la clave secreta utilizando el algoritmo HMAC-SHA que se utilizará para firmar el token JWT
					.signWith(Keys.hmacShaKeyFor(jwtSecreto.getBytes())) 
					.compact();


		} else if (tipoUsuario == 'D') {

			BancoDeAlimentos bancoDeAlimentos = bancoDeAlimentosRepository.findByEmail(email).orElseThrow();
			Date tokenExpirationDate = new Date(System.currentTimeMillis() + jwtDurationTokenEnSegundos * 1000);
			return Jwts.builder()
					.header().add("typ", TOKEN_TYPE).and()
					.subject(Long.toString(bancoDeAlimentos.getId()))
					.issuedAt(new Date())
					.expiration(tokenExpirationDate)
					.claim("email", email)
					.claim("fullname", bancoDeAlimentos.getUsername())
					// Esto develve los roles en String. Ejemplo [ADMIN, USER] --> "ADMIN, USER"
					.claim("roles", "BANCO_DE_ALIMENTOS"
						)
					//Crea la clave secreta utilizando el algoritmo HMAC-SHA que se utilizará para firmar el token JWT
					.signWith(Keys.hmacShaKeyFor(jwtSecreto.getBytes())) 
					.compact();
		} else {
			throw new IllegalArgumentException("El tipo de banco de alimentos no es válido.");
			
		}

		
	}
	
	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser()
							.verifyWith(Keys.hmacShaKeyFor(jwtSecreto.getBytes()))	// Se debe verificar la firma del token usando dicho algoritmo y secreto
							.build() // Crea el parseador del JWT
							.parseSignedClaims(token) //el parseador analizar el token pasado por parámetro
							.getPayload(); //Obtiene el payload del JWT
							
		return Long.parseLong(claims.getSubject());							
	}

	public String getEmailIdFromJWT(String token) {
		Claims claims = Jwts.parser()
							.verifyWith(Keys.hmacShaKeyFor(jwtSecreto.getBytes()))	// Se debe verificar la firma del token usando dicho algoritmo y secreto
							.build() // Crea el parseador del JWT
							.parseSignedClaims(token) //el parseador analizar el token pasado por parámetro
							.getPayload(); //Obtiene el payload del JWT
							
		return claims.get("email").toString();							
	}

	public String getRol(String token) {
		Claims claims = Jwts.parser()
							.verifyWith(Keys.hmacShaKeyFor(jwtSecreto.getBytes()))	// Se debe verificar la firma del token usando dicho algoritmo y secreto
							.build() // Crea el parseador del JWT
							.parseSignedClaims(token) //el parseador analizar el token pasado por parámetro
							.getPayload(); //Obtiene el payload del JWT
							
		return claims.get("roles").toString();						
	}
	
	public boolean validateToken(String authToken) {
		
		try {
			Jwts.parser().verifyWith(Keys.hmacShaKeyFor(jwtSecreto.getBytes()))
					.build().parseSignedClaims(authToken);
			return true;
		} catch (SecurityException ex) {
			log.info("Error en la firma del token JWT: " + ex.getMessage());
		} catch (MalformedJwtException ex) {
			log.info("Token malformado: " + ex.getMessage());
		} catch (ExpiredJwtException ex) {
			log.info("El token ha expirado: " + ex.getMessage());
		} catch (UnsupportedJwtException ex) {
			log.info("Token JWT no soportado: " + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			log.info("JWT claims vacío");
		}
		return false;
	}

	public char obtenerTipoUsuario(Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_EMPRESA"))) {
            return 'E'; // Empresa
        } else if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USUARIO"))) {
            return 'U'; // Usuario
        } else if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_BANCO_DE_ALIMENTOS"))) {
            return 'D'; // Banco de Alimentos
        }
        throw new IllegalArgumentException("El usuario no tiene un rol válido.");
    }

}
