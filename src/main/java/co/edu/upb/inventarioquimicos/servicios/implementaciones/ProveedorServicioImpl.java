package co.edu.upb.inventarioquimicos.servicios.implementaciones;

import co.edu.upb.inventarioquimicos.modelos.entidades.Proveedor;
import co.edu.upb.inventarioquimicos.repositorios.ProveedorRepositorio;
import co.edu.upb.inventarioquimicos.servicios.interfaces.ProveedorServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class ProveedorServicioImpl implements ProveedorServicio {
    private final ProveedorRepositorio proveedorRepositorio;

    @Override
    public Proveedor guardar(Proveedor proveedor) {
        return proveedorRepositorio.save(proveedor);
    }

    @Override
    public List<Proveedor> listarProveedores() {
        return proveedorRepositorio.findAll();
    }

    @Override
    public Optional<Proveedor> buscarPorId(Long id) {
        return proveedorRepositorio.findById(id);
    }

    @Override
    public List<Proveedor> listarPorNombre(String nombre) {
        return proveedorRepositorio.findByNombre(nombre);
    }

    @Override
    public void eliminar(Long id) {
        proveedorRepositorio.deleteById(id);
    }
}
