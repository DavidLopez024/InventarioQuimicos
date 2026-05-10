package co.edu.upb.inventarioquimicos.repositorios;

import co.edu.upb.inventarioquimicos.modelos.entidades.MovimientoInv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoInvRepositorio extends JpaRepository<MovimientoInv, Long> {
    List<MovimientoInv> findByFecha(LocalDate fecha);
}
