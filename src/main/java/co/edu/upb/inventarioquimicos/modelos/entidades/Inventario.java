package co.edu.upb.inventarioquimicos.modelos.entidades;

import co.edu.upb.inventarioquimicos.modelos.enums.EstadoInv;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "inventarios")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario", nullable = false, unique = true)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoInv estadoInv;

    @Column(name = "stock_disponible", nullable = false)
    private Double stockDisponible;

    @ManyToOne
    @JoinColumn(name = "lote_id", nullable = false)
    private Lote lote_id;
}
