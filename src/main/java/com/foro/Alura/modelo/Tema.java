package com.foro.Alura.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "temas")
public class Tema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String mensaje;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTema status;

    // Relación con Usuario (Autor)
    @ManyToOne(fetch = FetchType.EAGER)  // Cambié la estrategia a EAGER
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    // Relación con Curso
    @ManyToOne(fetch = FetchType.EAGER)  // Cambié la estrategia a EAGER
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;


    public Tema() {
        this.fechaCreacion = LocalDateTime.now();
    }


    public Tema(String titulo, String mensaje, Usuario autor, Curso curso, StatusTema status) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.autor = autor;
        this.curso = curso;
        this.status = status != null ? status : StatusTema.ACTIVO; // Si no se pasa un estado, se asume ACTIVO
        this.fechaCreacion = LocalDateTime.now();
    }


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

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    // Métodos específicos para cambiar el estado
    public void marcarComoCerrado() {
        this.status = StatusTema.CERRADO;
    }

    public void marcarComoResuelto() {
        this.status = StatusTema.RESUELTO;
    }

    public void marcarComoAbierto() {
        this.status = StatusTema.ABIERTO;
    }

    public void marcarComoActivo() {
        this.status = StatusTema.ACTIVO;
    }
}
