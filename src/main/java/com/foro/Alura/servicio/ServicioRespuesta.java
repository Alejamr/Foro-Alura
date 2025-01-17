package com.foro.Alura.servicio;



import com.foro.Alura.modelo.Respuesta;
import com.foro.Alura.repositorio.RespuestaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioRespuesta {

    private final RespuestaRepository respuestaRepository;

    public ServicioRespuesta(RespuestaRepository respuestaRepository) {
        this.respuestaRepository = respuestaRepository;
    }

    public List<Respuesta> listarRespuestas() {
        return respuestaRepository.findAll();
    }

    public Optional<Respuesta> obtenerRespuestaPorId(Long id) {
        return respuestaRepository.findById(id);
    }

    public Respuesta crearRespuesta(Respuesta respuesta) {
        return respuestaRepository.save(respuesta);
    }

    public Respuesta actualizarRespuesta(Long id, Respuesta respuestaActualizada) {
        return respuestaRepository.findById(id)
                .map(respuesta -> {
                    respuesta.setMensaje(respuestaActualizada.getMensaje());
                    respuesta.setSolucion(respuestaActualizada.getSolucion());
                    return respuestaRepository.save(respuesta);
                })
                .orElseThrow(() -> new IllegalArgumentException("Respuesta no encontrada con ID: " + id));
    }

    public void eliminarRespuesta(Long id) {
        if (respuestaRepository.existsById(id)) {
            respuestaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Respuesta no encontrada con ID: " + id);
        }
    }
}
