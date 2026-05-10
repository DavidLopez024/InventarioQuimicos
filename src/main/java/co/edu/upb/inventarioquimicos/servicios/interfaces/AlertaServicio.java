package co.edu.upb.inventarioquimicos.servicios.interfaces;

import co.edu.upb.inventarioquimicos.modelos.entidades.Alerta;
import co.edu.upb.inventarioquimicos.modelos.enums.TipoAle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AlertaServicio {
    Alerta guardar(Alerta alerta);
    List<Alerta> listarAlertas();
    Optional<Alerta>  buscarPorId(Long id);
    List<Alerta> listarPorTipoAle(TipoAle tipoAle);
    List<Alerta> listarPorFechaGeneracion(LocalDate fechaGeneracion);
    List<Alerta> listarPorAtendida(boolean atendida);
    void eliminar(Long id);
}
