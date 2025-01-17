package com.foro.Alura.controlador;

import com.foro.Alura.dto.RespuestaTema;
import com.foro.Alura.dto.SolicitudTema;
import com.foro.Alura.modelo.Curso;
import com.foro.Alura.modelo.StatusTema;
import com.foro.Alura.modelo.Tema;
import com.foro.Alura.modelo.Usuario;
import com.foro.Alura.servicio.ServicioCurso;
import com.foro.Alura.servicio.ServicioTema;
import com.foro.Alura.servicio.ServicioUsuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/temas")
public class TemaControlador {

    private final ServicioTema servicioTema;
    private final ServicioUsuario servicioUsuario;
    private final ServicioCurso servicioCurso;

    public TemaControlador(ServicioTema servicioTema, ServicioUsuario servicioUsuario, ServicioCurso servicioCurso) {
        this.servicioTema = servicioTema;
        this.servicioUsuario = servicioUsuario;
        this.servicioCurso = servicioCurso;
    }

    @GetMapping
    public ResponseEntity<List<RespuestaTema>> listarTemas() {
        List<RespuestaTema> temas = servicioTema.listarTemas();
        return ResponseEntity.ok(temas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaTema> obtenerTemaPorId(@PathVariable Long id) {
        return servicioTema.obtenerTemaPorId(id)
                .map(tema -> {
                    String autorNombre = (tema.getAutor() != null) ? tema.getAutor() : "Autor desconocido";
                    String cursoNombre = (tema.getCurso() != null) ? tema.getCurso() : "Curso desconocido";

                    RespuestaTema respuesta = new RespuestaTema(
                            tema.getId(),
                            tema.getTitulo(),
                            tema.getMensaje(),
                            tema.getFechaCreacion(),
                            tema.getStatus(),
                            autorNombre,
                            cursoNombre
                    );
                    return ResponseEntity.ok(respuesta);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<RespuestaTema> crearTema(@RequestBody SolicitudTema solicitudTema) {
        Usuario autor = (Usuario) servicioUsuario.obtenerUsuarioPorId(solicitudTema.getAutorId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + solicitudTema.getAutorId()));

        // Uso correcto del servicioCurso para obtener el curso por ID
        Curso curso = servicioCurso.obtenerCursoPorId(solicitudTema.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado con ID: " + solicitudTema.getCursoId()));

        Tema nuevoTema = new Tema(
                solicitudTema.getTitulo(),
                solicitudTema.getMensaje(),
                autor,
                curso,
                solicitudTema.getStatus()
        );

        Tema temaCreado = servicioTema.crearTema(nuevoTema);

        RespuestaTema respuesta = new RespuestaTema(
                temaCreado.getId(),
                temaCreado.getTitulo(),
                temaCreado.getMensaje(),
                temaCreado.getFechaCreacion(),
                temaCreado.getStatus(),
                temaCreado.getAutor().getNombre(),
                temaCreado.getCurso().getNombre()
        );

        return ResponseEntity.created(URI.create("/temas/" + temaCreado.getId())).body(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaTema> actualizarTema(@PathVariable Long id, @RequestBody SolicitudTema solicitudTema) {
        Usuario autor = (Usuario) servicioUsuario.obtenerUsuarioPorId(solicitudTema.getAutorId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + solicitudTema.getAutorId()));

        // Uso correcto del servicioCurso para obtener el curso por ID
        Curso curso = servicioCurso.obtenerCursoPorId(solicitudTema.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado con ID: " + solicitudTema.getCursoId()));

        Tema temaActualizado = new Tema(
                solicitudTema.getTitulo(),
                solicitudTema.getMensaje(),
                autor,
                curso,
                solicitudTema.getStatus()
        );

        Tema tema = servicioTema.actualizarTema(id, temaActualizado);

        RespuestaTema respuesta = new RespuestaTema(
                tema.getId(),
                tema.getTitulo(),
                tema.getMensaje(),
                tema.getFechaCreacion(),
                tema.getStatus(),
                tema.getAutor().getNombre(),
                tema.getCurso().getNombre()
        );

        return ResponseEntity.ok(respuesta);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<RespuestaTema> cambiarEstado(@PathVariable Long id, @RequestParam StatusTema nuevoEstado) {
        Tema temaActualizado = servicioTema.cambiarEstadoTema(id, nuevoEstado);

        RespuestaTema respuesta = new RespuestaTema(
                temaActualizado.getId(),
                temaActualizado.getTitulo(),
                temaActualizado.getMensaje(),
                temaActualizado.getFechaCreacion(),
                temaActualizado.getStatus(),
                temaActualizado.getAutor().getNombre(),
                temaActualizado.getCurso().getNombre()
        );

        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTema(@PathVariable Long id) {
        servicioTema.eliminarTema(id);
        return ResponseEntity.noContent().build();
    }
}
