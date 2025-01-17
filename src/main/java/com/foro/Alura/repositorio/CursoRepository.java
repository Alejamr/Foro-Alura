package com.foro.Alura.repositorio;



import com.foro.Alura.modelo.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
