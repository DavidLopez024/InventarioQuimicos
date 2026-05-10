package co.edu.upb.inventarioquimicos.servicios.implementaciones;

import co.edu.upb.inventarioquimicos.modelos.entidades.MovimientoInv;
import co.edu.upb.inventarioquimicos.repositorios.MovimientoInvRepositorio;
import co.edu.upb.inventarioquimicos.servicios.interfaces.MovimientoInvServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class MovimientoInvServicioImpl implements MovimientoInvServicio {
    private final MovimientoInvRepositorio movimientoInvRepositorio;

    @Override
    public MovimientoInv guardar(MovimientoInv movimientoInv) {
        return movimientoInvRepositorio.save(movimientoInv);
    }

    @Override
    public List<MovimientoInv> listarMovimientosInv() {
        return movimientoInvRepositorio.findAll();
    }

    @Override
    public Optional<MovimientoInv> buscarPorId(Long id) {
        return movimientoInvRepositorio.findById(id);
    }

    @Override
    public List<MovimientoInv> listarPorFecha(LocalDate fecha) {
        return movimientoInvRepositorio.findByFecha(fecha);
    }

    @Override
    public void eliminar(Long id) {
        movimientoInvRepositorio.deleteById(id);
    }
}
