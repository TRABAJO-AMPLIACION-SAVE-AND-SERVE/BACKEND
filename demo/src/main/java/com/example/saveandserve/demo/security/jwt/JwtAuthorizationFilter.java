package com.example.saveandserve.demo.security.jwt;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.saveandserve.demo.entity.Empresa;
import com.example.saveandserve.demo.entity.Usuario;
import com.example.saveandserve.demo.entity.BancoDeAlimentos;
import com.example.saveandserve.demo.service.EmpresaService;
import com.example.saveandserve.demo.service.UsuarioService;
import com.example.saveandserve.demo.service.BancoDeAlimentosService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider tokenProvider;
    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;
    private final BancoDeAlimentosService bancoDeAlimentosService;

   @Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			
			String token = getJwtFromRequest(request);
			
			if(StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
				
				String email = tokenProvider.getEmailIdFromJWT(token);
				String rol = tokenProvider.getRol(token);
				UserDetails user = usuarioService.loadUserByUsername(email);

				// PUEDE QUE HAYA QUE HACER UN SET PARA LOS ROLES EN ESTE MÉTODO
				UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(user, rol, user.getAuthorities());
				
				// Establece detalles adicional de autenticación si los hubiera (no en nuestr caso)
				authentication.setDetails(new WebAuthenticationDetails(request));	
				
				// Guardamos la autenticación en el contexto de seguridad
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
		} catch(Exception ex) {
			log.info("No se ha podido establecer la autenticación en el contexto de seguridad");
		}
		
		// Necesario para que la petición continua la cadena de filtros
		filterChain.doFilter(request, response);
	}
	
	private String getJwtFromRequest(HttpServletRequest request) {
		//Obtenemos el atriburo Authorization de la cabecera HTTP
		String bearerToken = request.getHeader(JwtProvider.TOKEN_HEADER);
		
		// Si la autenticacion es de tipo JWT
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProvider.TOKEN_PREFIX)) {
			//Extraemos del encabezado el lo que necesitamos, quitamos el Bearer
			return bearerToken.substring(JwtProvider.TOKEN_PREFIX.length(), bearerToken.length());
		}
		return null;
	}
}
