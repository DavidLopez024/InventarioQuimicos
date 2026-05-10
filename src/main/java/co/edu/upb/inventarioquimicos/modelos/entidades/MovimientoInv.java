package co.edu.upb.inventarioquimicos.modelos.entidades;

import co.edu.upb.inventarioquimicos.modelos.enums.TipoMov;
import co.edu.upb.inventarioquimicos.modelos.enums.UnidadMed;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movimientos_inv")
public class MovimientoInv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento_inv", nullable = false, unique = true)
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "producto")
    private String producto;

    @Column(name = "origen")
    private String origen;

    @Column(name = "destino")
    private String destino;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoMov tipoMov;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_med", nullable = false)
    private UnidadMed unidadMed;

    @Column(name = "observacion")
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
