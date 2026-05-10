package co.edu.upb.inventarioquimicos.modelos.enums;

import lombok.Getter;

@Getter
public enum EstadoInv {
    INSUMO_EN_BODEGA("Insumo en bodega"),
    EN_EMPAQUE("En empaque"),
    PUNTO_VENTA("Punto de venta");

    private final String descripcion;

    EstadoInv(String descripcion) {
        this.descripcion = descripcion;
    }
}
