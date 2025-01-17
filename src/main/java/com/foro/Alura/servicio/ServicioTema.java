package com.foro.Alura.servicio;

import com.foro.Alura.dto.RespuestaTema;
import com.foro.Alura.modelo.StatusTema;
import com.foro.Alura.modelo.Tema;
import com.foro.Alura.repositorio.TemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicioTema {

    private final TemaRepository temaRepository;

    public ServicioTema(TemaRepository temaRepository) {
        this.temaRepository = temaRepository;
    }

    // Listar todos los temas
    public List<RespuestaTema> listarTemas() {
        List<Tema> temas = temaRepository.findAll();

        // Forzar carga de relaciones para evitar null
        for (Tema tema : temas) {
            if (tema.getAutor() != null) {
                tema.getAutor().getNombre(); // Forzar carga de autor
            }
            if (tema.getCurso() != null) {
                tema.getCurso().getNombre(); // Forzar carga de curso
            }
        }

        return temas.stream()
                .map(this::convertirATemaDTO)
                .collect(Collectors.toList());
    }

    // Obtener un tema por ID
    public Optional<RespuestaTema> obtenerTemaPorId(Long id) {
        return temaRepository.findById(id)
                .map(tema -> {
                    // Forzar carga de relaciones si existen
                    if (tema.getAutor() != null) {
                        tema.getAutor().getNombre();
                    }
                    if (tema.getCurso() != null) {
                        tema.getCurso().getNombre();
                    }
                    return convertirATemaDTO(tema);
                });
    }

    // Crear un nuevo tema
    public Tema crearTema(Tema tema) {
        boolean existeDuplicado = temaRepository.existsByTituloAndMensaje(tema.getTitulo(), tema.getMensaje());
        if (existeDuplicado) {
            throw new IllegalArgumentException("Ya existe un tema con el mismo título y mensaje.");
        }
        return temaRepository.save(tema);
    }

    // Actualizar un tema existente
    public Tema actualizarTema(Long id, Tema temaActualizado) {
        return temaRepository.findById(id).map(tema -> {
            tema.setTitulo(temaActualizado.getTitulo());
            tema.setMensaje(temaActualizado.getMensaje());
            tema.setAutor(temaActualizado.getAutor());
            tema.setCurso(temaActualizado.getCurso());
            tema.setStatus(temaActualizado.getStatus());
            return temaRepository.save(tema);
        }).orElseThrow(() -> new IllegalArgumentException("No se encontró un tema con el ID especificado."));
    }

    // Cambiar el estado de un tema
    public Tema cambiarEstadoTema(Long id, StatusTema nuevoEstado) {
        return temaRepository.findById(id).map(tema -> {
            tema.setStatus(nuevoEstado);
            return temaRepository.save(tema);
        }).orElseThrow(() -> new IllegalArgumentException("No se encontró un tema con el ID especificado."));
    }

    // Cambiar el estado a CERRADO
    public Tema cerrarTema(Long id) {
        return cambiarEstadoTema(id, StatusTema.CERRADO);
    }

    // Cambiar el estado a RESUELTO
    public Tema resolverTema(Long id) {
        return cambiarEstadoTema(id, StatusTema.RESUELTO);
    }

    // Cambiar el estado a ABIERTO
    public Tema abrirTema(Long id) {
        return cambiarEstadoTema(id, StatusTema.ABIERTO);
    }

    // Cambiar el estado a ACTIVO
    public Tema activarTema(Long id) {
        return cambiarEstadoTema(id, StatusTema.ACTIVO);
    }

    // Eliminar un tema por ID
    public void eliminarTema(Long id) {
        if (temaRepository.existsById(id)) {
            temaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("No se encontró un tema con el ID especificado.");
        }
    }

    // Método privado para convertir un Tema a RespuestaTema
    private RespuestaTema convertirATemaDTO(Tema tema) {
        // Manejar posibles valores null para evitar NullPointerException
        String autorNombre = (tema.getAutor() != null) ? tema.getAutor().getNombre() : "Autor desconocido";
        String cursoNombre = (tema.getCurso() != null) ? tema.getCurso().getNombre() : "Curso desconocido";

        return new RespuestaTema(
                tema.getId(),
                tema.getTitulo(),
                tema.getMensaje(),
                tema.getFechaCreacion(),
                tema.getStatus(),
                autorNombre,
                cursoNombre
        );
    }
}
