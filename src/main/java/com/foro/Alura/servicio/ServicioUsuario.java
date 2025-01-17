package com.foro.Alura.servicio;

import com.foro.Alura.excepcion.UsuarioNoEncontradoException;
import com.foro.Alura.modelo.Tema;
import com.foro.Alura.modelo.Usuario;
import com.foro.Alura.repositorio.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioUsuario {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public ServicioUsuario(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para crear un nuevo usuario
    public Usuario crearUsuario(Usuario usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    // Método para actualizar un usuario existente
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNombre(usuarioActualizado.getNombre());
                    usuario.setEmail(usuarioActualizado.getEmail());
                    if (usuarioActualizado.getContrasena() != null) {
                        usuario.setContrasena(passwordEncoder.encode(usuarioActualizado.getContrasena()));
                    }
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));
    }

    // Método para listar los temas de un usuario específico
    public List<Tema> listarTemasPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario con ID " + usuarioId + " no encontrado"));
        return usuario.getTemas();
    }

    // Método para eliminar un tema de un usuario específico
    public void eliminarTema(Long usuarioId, Long temaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario con ID " + usuarioId + " no encontrado"));

        Tema temaAEliminar = usuario.getTemas().stream()
                .filter(tema -> tema.getId().equals(temaId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tema con ID " + temaId + " no encontrado para el usuario"));

        usuario.getTemas().remove(temaAEliminar);
        usuarioRepository.save(usuario);
    }

    // Método para listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll(); // Aquí debería devolver todos los usuarios de la base de datos
    }

    // Método para obtener un usuario por su ID
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id); // Realiza la consulta y devuelve un Optional
    }

    // Método para eliminar un usuario
    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario con ID " + id + " no encontrado"));
        usuarioRepository.delete(usuario); // Elimina el usuario encontrado
    }
}
