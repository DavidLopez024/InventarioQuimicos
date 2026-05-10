package co.edu.upb.inventarioquimicos.servicios.interfaces;

import co.edu.upb.inventarioquimicos.modelos.entidades.Proveedor;

import java.util.List;
import java.util.Optional;

public interface ProveedorServicio {
    Proveedor guardar(Proveedor proveedor);
    List<Proveedor> listarProveedores();
    Optional<Proveedor> buscarPorId(Long id);
    List<Proveedor> listarPorNombre(String nombre);
    void eliminar(Long id);
}
