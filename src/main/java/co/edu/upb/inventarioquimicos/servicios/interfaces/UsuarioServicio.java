package co.edu.upb.inventarioquimicos.servicios.interfaces;

import co.edu.upb.inventarioquimicos.modelos.entidades.Usuario;
import co.edu.upb.inventarioquimicos.modelos.enums.Rol;

import java.util.List;
import java.util.Optional;

public interface UsuarioServicio {
    Usuario guardar(Usuario usuario);
    List<Usuario> listarUsuarios();
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorUsername(String username);
    List<Usuario> listarPorNombre(String nombre);
    List<Usuario> listarPorRol(Rol rol);
    void eliminar(Long id);
}
