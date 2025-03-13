package com.example.saveandserve.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SolicitudIncorrectaException extends RuntimeException {
    public SolicitudIncorrectaException(String mensaje) {
        super(mensaje);
    }
}
