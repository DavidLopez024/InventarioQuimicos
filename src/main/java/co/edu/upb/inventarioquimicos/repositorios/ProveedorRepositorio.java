package co.edu.upb.inventarioquimicos.repositorios;

import co.edu.upb.inventarioquimicos.modelos.entidades.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProveedorRepositorio extends JpaRepository<Proveedor, Long> {
    List<Proveedor> findByNombre(String nombre);
}
