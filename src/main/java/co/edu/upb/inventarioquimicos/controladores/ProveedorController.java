package co.edu.upb.inventarioquimicos.controladores;

import co.edu.upb.inventarioquimicos.modelos.entidades.Proveedor;
import co.edu.upb.inventarioquimicos.servicios.interfaces.ProveedorServicio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ProveedorController {

    private static final Logger LOGGER = Logger.getLogger(ProveedorController.class.getName());

    @FXML private TextField nombre;
    @FXML private TextField contacto;
    @FXML private TextField telefono;
    @FXML private TextField correo;
    @FXML private TextField formaPago;
    @FXML private Label statusLabel;

    @Autowired
    private ProveedorServicio proveedorServicio;

    @Autowired
    private NavegacionService navegacionService;

    @FXML
    public void guardarProveedor() {

        if (nombre.getText().trim().isEmpty()) {
            mostrarAlerta("El nombre es obligatorio", Alert.AlertType.WARNING);
            return;
        }

        Proveedor p = new Proveedor();

        p.setNombre(nombre.getText().trim());
        p.setContacto(contacto.getText());
        p.setTelefono(telefono.getText());
        p.setCorreo(correo.getText());
        p.setFormaPago(formaPago.getText());

        proveedorServicio.guardar(p);

        mostrarEstado("✔ Proveedor creado correctamente", false);

        limpiarCampos();
    }

    @FXML
    public void irAListaProveedores() {
        navegacionService.cambiarVista("/vistas/proveedores-lista-view.fxml");
    }

    @FXML
    public void volver() {
        navegacionService.volverAPanel();
    }

    private void mostrarEstado(String mensaje, boolean esError) {
        statusLabel.setText(mensaje);
        statusLabel.setStyle(esError
                ? "-fx-text-fill: #c62828; -fx-font-size: 13px; -fx-font-weight: bold;"
                : "-fx-text-fill: #2e7d32; -fx-font-size: 13px; -fx-font-weight: bold;");
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Mensaje");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
        if (tipo != Alert.AlertType.INFORMATION) {
            mostrarEstado(mensaje, true);
        }
    }

    private void limpiarCampos() {
        nombre.clear();
        contacto.clear();
        telefono.clear();
        correo.clear();
        formaPago.clear();
    }
}