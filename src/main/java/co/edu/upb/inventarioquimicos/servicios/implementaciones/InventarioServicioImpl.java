package co.edu.upb.inventarioquimicos.servicios.implementaciones;

import co.edu.upb.inventarioquimicos.modelos.entidades.Inventario;
import co.edu.upb.inventarioquimicos.repositorios.InventarioRepositorio;
import co.edu.upb.inventarioquimicos.servicios.interfaces.InventarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class InventarioServicioImpl implements InventarioServicio {
    private final InventarioRepositorio inventarioRepositorio;

    @Override
    public Inventario guardar(Inventario inventario) {
        return inventarioRepositorio.save(inventario);
    }

    @Override
    public List<Inventario> listarInventarios() {
        return inventarioRepositorio.findAll();
    }

    @Override
    public Optional<Inventario> buscarPorId(Long id) {
        return inventarioRepositorio.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        inventarioRepositorio.deleteById(id);
    }
}
