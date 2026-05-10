package co.edu.upb.inventarioquimicos.controladores;

import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BodegueroController {

    @Autowired
    private NavegacionService navegacionService;

    @FXML
    public void registrarEntrada() {
        navegacionService.cambiarVista("/vistas/registro-lote-view.fxml");
    }

    @FXML
    public void verInventario() {
        navegacionService.cambiarVista("/vistas/inventario-view.fxml");
    }

    @FXML
    public void irAPerdidas() {
        navegacionService.cambiarVista("/vistas/perdida-lote-view.fxml");
    }

    @FXML
    public void verMovimientos() {
        navegacionService.cambiarVista("/vistas/movimientos-view.fxml");
    }

    @FXML
    public void irATraslado() {
        navegacionService.cambiarVista("/vistas/movimiento-traslado-view.fxml");
    }

    @FXML
    public void logout() {
        navegacionService.cambiarVista("/vistas/login-view.fxml");
    }
}
