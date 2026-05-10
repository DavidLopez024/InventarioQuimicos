package co.edu.upb.inventarioquimicos.servicios.implementaciones;

import co.edu.upb.inventarioquimicos.modelos.entidades.Alerta;
import co.edu.upb.inventarioquimicos.modelos.enums.TipoAle;
import co.edu.upb.inventarioquimicos.repositorios.AlertaRepositorio;
import co.edu.upb.inventarioquimicos.servicios.interfaces.AlertaServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class AlertaServicioImpl implements AlertaServicio {
    private final AlertaRepositorio alertaRepositorio;

    @Override
    public Alerta guardar(Alerta alerta) {
        return alertaRepositorio.save(alerta);
    }

    @Override
    public List<Alerta> listarAlertas() {
        return alertaRepositorio.findAll();
    }

    @Override
    public Optional<Alerta> buscarPorId(Long id) {
        return alertaRepositorio.findById(id);
    }

    @Override
    public List<Alerta> listarPorTipoAle(TipoAle tipoAle) {
        return alertaRepositorio.findByTipoAle(tipoAle);
    }

    @Override
    public List<Alerta> listarPorFechaGeneracion(LocalDate fechaGeneracion) {
        return alertaRepositorio.findByFechaGeneracion(fechaGeneracion);
    }

    @Override
    public List<Alerta> listarPorAtendida(boolean atendida) {
        return alertaRepositorio.findByAtendida(atendida);
    }

    @Override
    public void eliminar(Long id) {
        alertaRepositorio.deleteById(id);
    }
}
