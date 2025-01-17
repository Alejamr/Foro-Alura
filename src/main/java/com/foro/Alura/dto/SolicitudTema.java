package com.foro.Alura.dto;
import com.foro.Alura.modelo.Curso;
import com.foro.Alura.modelo.StatusTema;
import com.foro.Alura.modelo.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class SolicitudTema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título no puede estar vacío.")
    private String titulo;

    @NotBlank(message = "El mensaje no puede estar vacío.")
    private String mensaje;

    @NotNull(message = "El autor no puede ser nulo.")
    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @NotNull(message = "El curso no puede ser nulo.")
    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @NotNull(message = "El estado del tema no puede ser nulo.")
    @Enumerated(EnumType.STRING)
    private StatusTema status;

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

    public StatusTema getStatus() {
        return status;
    }

    public void setStatus(StatusTema status) {
        this.status = status;
    }

    public Long getAutorId() {
        return autor.getId();
    }

    public Long getCursoId() {
        return curso.getId();
    }
}
