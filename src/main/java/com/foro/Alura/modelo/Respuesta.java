package com.foro.Alura.modelo;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mensaje;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "tema_id", nullable = false)
    private Tema tema;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @Column(nullable = false)
    private Boolean solucion = false;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Boolean getSolucion() {
        return solucion;
    }

    public void setSolucion(Boolean solucion) {
        this.solucion = solucion;
    }

    // Método getTitulo debe devolver el título relacionado con la respuesta, si no hay título puedes devolver el tema
    public String getTitulo() {
        return this.tema != null ? this.tema.getTitulo() : "";
    }

    // Método getStatus devuelve el StatusTema asociado al Tema de la respuesta
    public StatusTema getStatus() {
        return this.tema != null ? this.tema.getStatus() : null;
    }

    // Método getCurso devuelve el Curso asociado al Tema de la respuesta
    public Curso getCurso() {
        return this.tema != null ? this.tema.getCurso() : null;
    }
}
