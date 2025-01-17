package com.foro.Alura.repositorio;


import com.foro.Alura.modelo.StatusTema;
import com.foro.Alura.modelo.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TemaRepository extends JpaRepository<Tema, Long> {
    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    List<Tema> findByStatus(StatusTema status);

    @Query("SELECT t FROM Tema t JOIN FETCH t.autor JOIN FETCH t.curso WHERE t.status = :status")
    List<Tema> findByStatusWithAutorAndCurso(@Param("status") StatusTema status);

    @Query("SELECT t FROM Tema t JOIN FETCH t.autor JOIN FETCH t.curso")
    List<Tema> findAllWithAutorAndCurso();
}
