package com.foro.Alura.servicio;



import com.foro.Alura.modelo.Curso;
import com.foro.Alura.repositorio.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicioCurso {

    private final CursoRepository cursoRepository;

    public ServicioCurso(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public Optional<Curso> obtenerCursoPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public Curso crearCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Curso actualizarCurso(Long id, Curso cursoActualizado) {
        if (cursoRepository.existsById(id)) {
            cursoActualizado.setId(id);
            return cursoRepository.save(cursoActualizado);
        } else {
            throw new IllegalArgumentException("Curso no encontrado con ID: " + id);
        }
    }

    public void eliminarCurso(Long id) {
        if (cursoRepository.existsById(id)) {
            cursoRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Curso no encontrado con ID: " + id);
        }
    }
}
