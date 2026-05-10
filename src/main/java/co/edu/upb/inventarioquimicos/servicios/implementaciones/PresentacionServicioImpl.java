package co.edu.upb.inventarioquimicos.servicios.implementaciones;

import co.edu.upb.inventarioquimicos.modelos.entidades.Presentacion;
import co.edu.upb.inventarioquimicos.modelos.entidades.Producto;
import co.edu.upb.inventarioquimicos.repositorios.PresentacionRepositorio;
import co.edu.upb.inventarioquimicos.repositorios.ProductoRepositorio;
import co.edu.upb.inventarioquimicos.servicios.interfaces.PresentacionServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class PresentacionServicioImpl implements PresentacionServicio {
    private final PresentacionRepositorio presentacionRepositorio;

    @Override
    public Presentacion guardar(Presentacion presentacion) {
        return presentacionRepositorio.save(presentacion);
    }

    @Override
    public List<Presentacion> listarPresentaciones() {
        return presentacionRepositorio.findAll();
    }

    @Override
    public Optional<Presentacion> buscarPorId(Long id) {
        return presentacionRepositorio.findById(id);
    }

    @Override
    public List<Presentacion> listarPorNombre(String nombre) {
        return presentacionRepositorio.findByNombre(nombre);
    }

    @Override
    public void eliminar(Long id) {
        presentacionRepositorio.deleteById(id);
    }
}
