package co.edu.upb.inventarioquimicos.modelos.entidades;

import co.edu.upb.inventarioquimicos.modelos.enums.UnidadMed;
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
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id_producto", nullable = false, unique = true)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "stock_minimo")
    private Double stockMinimo;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_med", nullable = false)
    private UnidadMed unidadMed;

    @Override
    public String toString() {
        return nombre;
    }
}
