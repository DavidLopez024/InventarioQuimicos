package co.edu.upb.inventarioquimicos.repositorios;

import co.edu.upb.inventarioquimicos.modelos.entidades.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepositorio extends JpaRepository<Inventario, Long> {

}
