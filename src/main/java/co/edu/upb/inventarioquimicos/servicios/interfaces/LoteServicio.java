package co.edu.upb.inventarioquimicos.servicios.interfaces;

import co.edu.upb.inventarioquimicos.modelos.entidades.Lote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoteServicio {
    Lote guardar(Lote lote);
    List<Lote> listarLotes();
    Optional<Lote> buscarPorId(Long id);
    List<Lote> buscarPorFechaIngreso(LocalDate fechaIngreso);
    List<Lote> buscarPorFechaVencimiento(LocalDate fechaVencimiento);
    List<Lote> buscarPorProductoId(Long productoId);
    List<Lote> buscarPorProductoIdConStock(Long productoId);
    java.util.Optional<Lote> buscarPorNumeroLote(String numeroLote);
    void eliminar(Long id);
}
