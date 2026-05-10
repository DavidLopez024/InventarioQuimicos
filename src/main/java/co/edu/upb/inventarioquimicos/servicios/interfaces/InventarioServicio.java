package co.edu.upb.inventarioquimicos.servicios.interfaces;

import co.edu.upb.inventarioquimicos.modelos.entidades.Inventario;

import java.util.List;
import java.util.Optional;

public interface InventarioServicio {
    Inventario guardar(Inventario inventario);
    List<Inventario> listarInventarios();
    Optional<Inventario> buscarPorId(Long id);
    void eliminar(Long id);
}
