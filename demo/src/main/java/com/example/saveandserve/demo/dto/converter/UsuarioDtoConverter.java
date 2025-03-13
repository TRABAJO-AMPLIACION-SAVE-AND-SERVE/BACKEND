package com.example.saveandserve.demo.dto.converter;

import com.example.saveandserve.demo.dto.GetUserDto;
import com.example.saveandserve.demo.entity.Usuario;

import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UsuarioDtoConverter {

    public GetUserDto convertUserEntityToGetUserDto(Usuario user) {
        return GetUserDto.builder()
                .username(user.getUsername())
                .roles(Collections.singleton(user.getRol().name()))
                .build();
    }
}
