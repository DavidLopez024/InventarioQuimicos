package co.edu.upb.inventarioquimicos.servicios.interfaces;

import co.edu.upb.inventarioquimicos.modelos.entidades.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoServicio {
    Producto guardar(Producto producto);
    List<Producto> listarProductos();
    Optional<Producto> buscarPorId(Long id);
    List<Producto> listarPorNombre(String nombre);
     void eliminar(Long id);
}
