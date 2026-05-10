package co.edu.upb.inventarioquimicos.modelos.enums;

import lombok.Getter;

@Getter
public enum TipoAle {
    STOCK_BAJO("Stock bajo"),
    PROXIMO_VENCIMIENTO("Próximo a vencer"),
    LOTE_VENCIDO("Lote vencido");

    private final String descripcion;

    TipoAle(String descripcion) {
        this.descripcion = descripcion;
    }
}
