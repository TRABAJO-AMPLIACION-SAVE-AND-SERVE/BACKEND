package com.example.saveandserve.demo.service;

import com.example.saveandserve.demo.entity.Usuario;
import com.example.saveandserve.demo.entity.Empresa;
import com.example.saveandserve.demo.entity.BancoDeAlimentos;
import com.example.saveandserve.demo.repository.UsuarioRepository;
import com.example.saveandserve.demo.repository.EmpresaRepository;
import com.example.saveandserve.demo.repository.BancoDeAlimentosRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final BancoDeAlimentosRepository bancoDeAlimentosRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent()) {
            return User.builder()
                    .username(usuario.get().getEmail())
                    .password(usuario.get().getPassword())
                    .roles("ADMIN")
                    .build();
        }

        Optional<Empresa> empresa = empresaRepository.findByEmail(email);
        if (empresa.isPresent()) {
            return User.builder()
                    .username(empresa.get().getEmail())
                    .password(empresa.get().getContrasenia()) // Contraseña encriptada
                    .roles("EMPRESA")
                    .build();
        }

        Optional<BancoDeAlimentos> banco = bancoDeAlimentosRepository.findByEmail(email);
        if (banco.isPresent()) {
            return User.builder()
                    .username(banco.get().getEmail())
                    .password(banco.get().getContrasenia()) // Contraseña encriptada
                    .roles("BANCO_DE_ALIMENTOS")
                    .build();
        }

        throw new UsernameNotFoundException("Usuario no encontrado con el email: " + email);
    }

    public Usuario loadUserById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el ID: " + id));
    }

    public Usuario saveUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // 🔒 Encripta antes de guardar
        return usuarioRepository.save(usuario);
    }
}
