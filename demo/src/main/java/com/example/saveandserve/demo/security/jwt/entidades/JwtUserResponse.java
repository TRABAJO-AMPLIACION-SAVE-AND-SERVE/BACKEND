package com.example.saveandserve.demo.security.jwt.entidades;

import java.util.Set;

import com.example.saveandserve.demo.dto.GetUserDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtUserResponse extends GetUserDto {

    private String token;

    @Builder(builderMethodName = "jwtUserResponseBuilder")
    public JwtUserResponse(String username, String email, Set<String> roles, String token) {
        super(username, email,roles);
        this.token = token;
    }
}
