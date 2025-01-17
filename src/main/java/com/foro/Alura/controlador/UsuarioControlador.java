package com.foro.Alura.controlador;


import com.foro.Alura.modelo.Usuario;
import com.foro.Alura.servicio.ServicioUsuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {

    private final ServicioUsuario servicioUsuario;

    public UsuarioControlador(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = servicioUsuario.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = servicioUsuario.obtenerUsuarioPorId(id);
        return usuarioOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioCreado = servicioUsuario.crearUsuario(usuario);
        return ResponseEntity.created(URI.create("/usuarios/" + usuarioCreado.getId())).body(usuarioCreado);
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = servicioUsuario.actualizarUsuario(id, usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        servicioUsuario.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
