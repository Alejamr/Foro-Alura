package com.foro.Alura.dto;

import com.foro.Alura.modelo.StatusTema;
import com.foro.Alura.modelo.Tema;

import java.time.LocalDateTime;

// Clase RespuestaTema
public class RespuestaTema {

    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private StatusTema status;
    private String autorNombre;
    private String cursoNombre;

    // Constructor completo
    public RespuestaTema(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion,
                         StatusTema status, String autorNombre, String cursoNombre) {
        this.id = id;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = fechaCreacion;
        this.status = status;
        this.autorNombre = autorNombre;
        this.cursoNombre = cursoNombre;
    }

    // Constructor predeterminado
    public RespuestaTema() {
    }

    // Constructor para transformar un Tema en RespuestaTema
    public RespuestaTema(Tema tema) {
        this.id = tema.getId();
        this.titulo = tema.getTitulo();
        this.mensaje = tema.getMensaje();
        this.fechaCreacion = tema.getFechaCreacion();
        this.status = tema.getStatus();

        // Nombres del autor y curso
        this.autorNombre = (tema.getAutor() != null) ? tema.getAutor().getNombre() : "Autor desconocido";
        this.cursoNombre = (tema.getCurso() != null) ? tema.getCurso().getNombre() : "Curso desconocido";
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public StatusTema getStatus() {
        return status;
    }

    public void setStatus(StatusTema status) {
        this.status = status;
    }

    public String getAutorNombre() {
        return autorNombre;
    }

    public void setAutorNombre(String autorNombre) {
        this.autorNombre = autorNombre;
    }

    public String getCursoNombre() {
        return cursoNombre;
    }

    public void setCursoNombre(String cursoNombre) {
        this.cursoNombre = cursoNombre;
    }

    // Métodos de retorno que no devuelven null
    public String getAutor() {
        return autorNombre != null ? autorNombre : "Autor no disponible";
    }

    public String getCurso() {
        return cursoNombre != null ? cursoNombre : "Curso no disponible";
    }
}
