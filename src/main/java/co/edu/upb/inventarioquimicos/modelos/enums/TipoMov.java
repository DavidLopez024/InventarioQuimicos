package co.edu.upb.inventarioquimicos.modelos.enums;

import jakarta.persistence.EnumeratedValue;
import lombok.Getter;

@Getter
public enum TipoMov {
    ENTRADA("Entrada"),
    SALIDA("Salida"),
    REENVASADO("Reenvasado"),
    AJUSTE("Ajuste"),
    PERDIDA("Pérdida");

    @EnumeratedValue
    private final String descripcion;

    TipoMov(String descripcion) {
        this.descripcion = descripcion;
    }
}
