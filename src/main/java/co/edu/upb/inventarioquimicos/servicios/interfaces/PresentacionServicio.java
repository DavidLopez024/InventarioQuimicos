package co.edu.upb.inventarioquimicos.servicios.interfaces;

import co.edu.upb.inventarioquimicos.modelos.entidades.Presentacion;
import co.edu.upb.inventarioquimicos.modelos.entidades.Producto;

import java.util.List;
import java.util.Optional;

public interface PresentacionServicio {
    Presentacion guardar(Presentacion presentacion);
    List<Presentacion> listarPresentaciones();
    Optional<Presentacion> buscarPorId(Long id);
    List<Presentacion> listarPorNombre(String nombre);
    void eliminar(Long id);
}
