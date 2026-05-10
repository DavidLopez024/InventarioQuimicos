package co.edu.upb.inventarioquimicos.servicios.implementaciones;

import co.edu.upb.inventarioquimicos.modelos.entidades.Lote;
import co.edu.upb.inventarioquimicos.repositorios.LoteRepositorio;
import co.edu.upb.inventarioquimicos.servicios.interfaces.LoteServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class LoteServicioImpl implements LoteServicio {
    private final LoteRepositorio loteRepositorio;


    @Override
    public Lote guardar(Lote lote) {
        return loteRepositorio.save(lote);
    }

    @Override
    public List<Lote> listarLotes() {
        return loteRepositorio.findAll();
    }

    @Override
    public Optional<Lote> buscarPorId(Long id) {
        return loteRepositorio.findById(id);
    }

    @Override
    public List<Lote> buscarPorFechaIngreso(LocalDate fechaIngreso) {
        return loteRepositorio.findByFechaIngreso(fechaIngreso);
    }

    @Override
    public List<Lote> buscarPorFechaVencimiento(LocalDate fechaVencimiento) {
        return loteRepositorio.findByFechaVencimiento(fechaVencimiento);
    }

    @Override
    public List<Lote> buscarPorProductoId(Long productoId) {
        return loteRepositorio.findByProducto_Id(productoId);
    }

    @Override
    public List<Lote> buscarPorProductoIdConStock(Long productoId) {
        return loteRepositorio.findByProducto_IdAndCantidadGreaterThan(productoId, 0.0);
    }

    @Override
    public java.util.Optional<Lote> buscarPorNumeroLote(String numeroLote) {
        return loteRepositorio.findByNumeroLote(numeroLote);
    }

    @Override
    public void eliminar(Long id) {
        loteRepositorio.deleteById(id);
    }
}
