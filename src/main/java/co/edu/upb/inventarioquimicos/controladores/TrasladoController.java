package co.edu.upb.inventarioquimicos.controladores;

import co.edu.upb.inventarioquimicos.modelos.entidades.MovimientoInv;
import co.edu.upb.inventarioquimicos.modelos.entidades.Producto;
import co.edu.upb.inventarioquimicos.modelos.enums.TipoMov;
import co.edu.upb.inventarioquimicos.servicios.interfaces.MovimientoInvServicio;
import co.edu.upb.inventarioquimicos.servicios.interfaces.ProductoServicio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.logging.Logger;

@Component
public class TrasladoController {

    private static final Logger LOGGER = Logger.getLogger(TrasladoController.class.getName());

    @FXML
    private ComboBox<Producto> productoCombo;

    @FXML
    private ComboBox<String> origenCombo;

    @FXML
    private ComboBox<String> destinoCombo;

    @FXML
    private TextField cantidad;

    @FXML
    private Label statusLabel;

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private MovimientoInvServicio movimientoInvServicio;

    @Autowired
    private NavegacionService navegacionService;

    @FXML
    public void initialize() {
        productoCombo.getItems().setAll(productoServicio.listarProductos());

        origenCombo.getItems().addAll("INVENTARIO", "EMPAQUETAMIENTO", "PUNTO_VENTA");
        destinoCombo.getItems().addAll("INVENTARIO", "EMPAQUETAMIENTO", "PUNTO_VENTA");
    }

    @FXML
    public void registrarTraslado() {

        if (productoCombo.getValue() == null ||
                origenCombo.getValue() == null ||
                destinoCombo.getValue() == null ||
                cantidad.getText().isEmpty()) {

            mostrarAlerta("Complete todos los campos", Alert.AlertType.WARNING);
            return;
        }

        if (origenCombo.getValue().equals(destinoCombo.getValue())) {
            mostrarAlerta("El origen y el destino no pueden ser iguales", Alert.AlertType.WARNING);
            return;
        }

        double cantidadValor;

        try {
            cantidadValor = Double.parseDouble(cantidad.getText());
        } catch (Exception e) {
            mostrarAlerta("Ingrese una cantidad válida", Alert.AlertType.ERROR);
            return;
        }

        if (cantidadValor <= 0) {
            mostrarAlerta("La cantidad debe ser mayor a 0", Alert.AlertType.WARNING);
            return;
        }

        MovimientoInv mov = new MovimientoInv();

        mov.setFecha(LocalDate.now());
        mov.setTipoMov(TipoMov.SALIDA);
        mov.setUnidadMed(productoCombo.getValue().getUnidadMed());

        mov.setProducto(productoCombo.getValue().getNombre());
        mov.setOrigen(origenCombo.getValue());
        mov.setDestino(destinoCombo.getValue());
        mov.setObservaciones("Traslado de " + cantidadValor);
        mov.setUsuario(usuarioSesion.getUsuario());

        movimientoInvServicio.guardar(mov);

        mostrarEstado("✔ Traslado registrado correctamente", false);
        cantidad.clear();
    }

    private void mostrarEstado(String mensaje, boolean esError) {
        statusLabel.setText(mensaje);
        statusLabel.setStyle(esError
                ? "-fx-text-fill: #c62828; -fx-font-size: 13px; -fx-font-weight: bold;"
                : "-fx-text-fill: #2e7d32; -fx-font-size: 13px; -fx-font-weight: bold;");
    }

    private void mostrarAlerta(String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Mensaje");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
        if (tipo != Alert.AlertType.INFORMATION) {
            mostrarEstado(msg, true);
        }
    }

    @FXML
    public void volver() {
        navegacionService.volverAPanel();
    }
}