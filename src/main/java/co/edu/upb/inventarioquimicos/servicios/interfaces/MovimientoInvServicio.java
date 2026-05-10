package co.edu.upb.inventarioquimicos.servicios.interfaces;

import co.edu.upb.inventarioquimicos.modelos.entidades.MovimientoInv;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovimientoInvServicio {
    MovimientoInv guardar(MovimientoInv movimientoInv);
    List<MovimientoInv> listarMovimientosInv();
    Optional<MovimientoInv> buscarPorId(Long id);
    List<MovimientoInv> listarPorFecha(LocalDate fecha);
    void eliminar(Long id);
}
