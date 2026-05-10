package co.edu.upb.inventarioquimicos.servicios.implementaciones;

import co.edu.upb.inventarioquimicos.modelos.entidades.Producto;
import co.edu.upb.inventarioquimicos.repositorios.ProductoRepositorio;
import co.edu.upb.inventarioquimicos.servicios.interfaces.ProductoServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class ProductoServicioImpl implements ProductoServicio {
    private final ProductoRepositorio productoRepositorio;

    @Override
    public Producto guardar(Producto producto) {
        return productoRepositorio.save(producto);
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepositorio.findAll();
    }

    @Override
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepositorio.findById(id);
    }

    @Override
    public List<Producto> listarPorNombre(String nombre) {
        return productoRepositorio.findByNombre(nombre);
    }

    @Override
    public void eliminar(Long id) {
        productoRepositorio.deleteById(id);
    }
}
