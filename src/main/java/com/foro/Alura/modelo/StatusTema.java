package com.foro.Alura.modelo;


public enum StatusTema {
    ACTIVO("Activo"),
    CERRADO("Cerrado"),
    RESUELTO("Resuelto"),
    ABIERTO("Abierto");

    private final String descripcion;

    StatusTema(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
