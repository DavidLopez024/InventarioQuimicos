package co.edu.upb.inventarioquimicos.repositorios;

import co.edu.upb.inventarioquimicos.modelos.entidades.Alerta;
import co.edu.upb.inventarioquimicos.modelos.enums.TipoAle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AlertaRepositorio extends JpaRepository<Alerta, Long> {
    List<Alerta> findByTipoAle(TipoAle tipoAle);
    List<Alerta> findByFechaGeneracion(LocalDate fechaGeneracion);
    List<Alerta> findByAtendida(boolean atendida);
}
