package co.edu.upb.inventarioquimicos.repositorios;

import co.edu.upb.inventarioquimicos.modelos.entidades.Usuario;
import co.edu.upb.inventarioquimicos.modelos.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    List<Usuario> findByNombre(String nombre);
    List<Usuario> findByRol(Rol rol);
}
