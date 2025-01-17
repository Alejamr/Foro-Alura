package com.foro.Alura.controlador;

import com.foro.Alura.dto.RespuestaTema;
import com.foro.Alura.modelo.Respuesta;

import com.foro.Alura.servicio.ServicioRespuesta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/respuestas")
public class RespuestaControlador {

    private final ServicioRespuesta servicioRespuesta;

    public RespuestaControlador(ServicioRespuesta servicioRespuesta) {
        this.servicioRespuesta = servicioRespuesta;
    }

    @GetMapping
    public ResponseEntity<List<RespuestaTema>> listarRespuestas() {
        List<Respuesta> respuestas = servicioRespuesta.listarRespuestas();
        List<RespuestaTema> respuestaTemas = respuestas.stream()
                .map(respuesta -> new RespuestaTema(
                        respuesta.getId(),
                        respuesta.getTitulo(),
                        respuesta.getMensaje(),
                        respuesta.getFechaCreacion(),
                        respuesta.getStatus(),
                        respuesta.getAutor().getNombre(),  // Asumimos que 'getNombre()' existe en la clase Usuario
                        respuesta.getCurso().getNombre()   // Asumimos que 'getNombre()' existe en la clase Curso
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(respuestaTemas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaTema> obtenerRespuestaPorId(@PathVariable Long id) {
        return servicioRespuesta.obtenerRespuestaPorId(id)
                .map(respuesta -> new RespuestaTema(
                        respuesta.getId(),
                        respuesta.getTitulo(),
                        respuesta.getMensaje(),
                        respuesta.getFechaCreacion(),
                        respuesta.getStatus(),
                        respuesta.getAutor().getNombre(),
                        respuesta.getCurso().getNombre()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RespuestaTema> crearRespuesta(@RequestBody Respuesta respuesta) {
        Respuesta respuestaCreada = servicioRespuesta.crearRespuesta(respuesta);
        RespuestaTema respuestaTema = new RespuestaTema(
                respuestaCreada.getId(),
                respuestaCreada.getTitulo(),
                respuestaCreada.getMensaje(),
                respuestaCreada.getFechaCreacion(),
                respuestaCreada.getStatus(),
                respuestaCreada.getAutor().getNombre(),
                respuestaCreada.getCurso().getNombre()
        );
        return ResponseEntity.created(URI.create("/respuestas/" + respuestaCreada.getId())).body(respuestaTema);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaTema> actualizarRespuesta(@PathVariable Long id, @RequestBody Respuesta respuesta) {
        Respuesta respuestaActualizada = servicioRespuesta.actualizarRespuesta(id, respuesta);
        RespuestaTema respuestaTema = new RespuestaTema(
                respuestaActualizada.getId(),
                respuestaActualizada.getTitulo(),
                respuestaActualizada.getMensaje(),
                respuestaActualizada.getFechaCreacion(),
                respuestaActualizada.getStatus(),
                respuestaActualizada.getAutor().getNombre(),
                respuestaActualizada.getCurso().getNombre()
        );
        return ResponseEntity.ok(respuestaTema);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRespuesta(@PathVariable Long id) {
        servicioRespuesta.eliminarRespuesta(id);
        return ResponseEntity.noContent().build();
    }
}
