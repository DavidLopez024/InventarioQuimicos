package co.edu.upb.inventarioquimicos.modelos.enums;

import jakarta.persistence.EnumeratedValue;
import lombok.Getter;

@Getter
public enum Rol {
    ADMINISTRADOR("Administrador"),
    BODEGUERO("Bodeguero");

    @EnumeratedValue
    private final String nombre;

    Rol(String nombre) {
        this.nombre = nombre;
    }
}
