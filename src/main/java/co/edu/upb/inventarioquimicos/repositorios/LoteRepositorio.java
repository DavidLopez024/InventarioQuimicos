package co.edu.upb.inventarioquimicos.repositorios;

import co.edu.upb.inventarioquimicos.modelos.entidades.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LoteRepositorio extends JpaRepository<Lote, Long> {
    List<Lote> findByFechaIngreso(LocalDate fechaIngreso);
    List<Lote> findByFechaVencimiento(LocalDate fechaVencimiento);
    List<Lote> findByProducto_Id(Long productoId);
    List<Lote> findByProducto_IdAndCantidadGreaterThan(Long productoId, Double cantidad);
    java.util.Optional<Lote> findByNumeroLote(String numeroLote);
}
