package co.edu.upb.inventarioquimicos.modelos.enums;

import jakarta.persistence.EnumeratedValue;
import lombok.Getter;

@Getter
public enum UnidadMed {
    KILOGRAMO("kg"),
    LIBRA("lb"),
    GRAMO("g"),
    LITRO("L"),
    MILILITRO("mL"),
    UNIDAD("unidad");

    @EnumeratedValue
    private final String simbolo;

    UnidadMed(String simbolo) {
        this.simbolo = simbolo;
    }
}
