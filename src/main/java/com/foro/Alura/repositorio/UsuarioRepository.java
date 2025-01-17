package com.foro.Alura.repositorio;





import com.foro.Alura.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
