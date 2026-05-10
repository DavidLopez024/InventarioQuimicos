package co.edu.upb.inventarioquimicos.modelos.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lotes")
public class Lote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lote", nullable = false, unique = true)
    private Long id;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "cantidad", nullable = false)
    private Double cantidad;

    @Column(name = "costo", nullable = false)
    private Double costo;

    @Column(name = "numero_lote", unique = true, length = 50)
    private String numeroLote;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = true)
    private Proveedor proveedor;

    @Override
    public String toString() {
        String numStr = (numeroLote != null && !numeroLote.isBlank()) ? numeroLote : String.valueOf(id);
        return "Lote " + numStr + " | Cant: " + cantidad + " | Vence: " + fechaVencimiento;
    }
}
