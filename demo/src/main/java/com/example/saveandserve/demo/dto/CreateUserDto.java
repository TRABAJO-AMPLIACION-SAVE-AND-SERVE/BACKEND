package com.example.saveandserve.demo.dto;

import lombok.Data;

@Data
public class CreateUserDto {    
	private String nombreUsuario;
	private String password;
	private String password2;
}        

