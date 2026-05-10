package co.edu.upb.inventarioquimicos.repositorios;

import co.edu.upb.inventarioquimicos.modelos.entidades.Presentacion;
import co.edu.upb.inventarioquimicos.modelos.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PresentacionRepositorio extends JpaRepository<Presentacion, Long> {
    List<Presentacion> findByNombre(String nombre);
}
