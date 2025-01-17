package com.foro.Alura.dto;

public class RespuestaAutenticacion {
    private String token;

    public RespuestaAutenticacion(String token) {
        this.token = token;
    }

    // Getter y setter
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
