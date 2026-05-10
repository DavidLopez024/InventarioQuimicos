package co.edu.upb.inventarioquimicos.controladores;

import co.edu.upb.inventarioquimicos.modelos.entidades.Producto;
import co.edu.upb.inventarioquimicos.modelos.enums.UnidadMed;
import co.edu.upb.inventarioquimicos.servicios.interfaces.ProductoServicio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ProductoController {

    private static final Logger LOGGER = Logger.getLogger(ProductoController.class.getName());

    @FXML
    private TextField nombre;

    @FXML
    private TextField stockMinimo;

    @FXML
    private ComboBox<UnidadMed> unidadMedida;

    @FXML
    private Label statusLabel;

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private NavegacionService navegacionService;

    private Producto productoSeleccionado;

    @FXML
    public void initialize() {
        unidadMedida.getItems().setAll(UnidadMed.values());
    }

    @FXML
    public void guardarProducto() {

        if (nombre.getText().trim().isEmpty()) {
            mostrarAlerta("El nombre no puede estar vacío", Alert.AlertType.WARNING);
            return;
        }

        double stock;
        try {
            stock = Double.parseDouble(stockMinimo.getText());

            if (stock < 0) {
                mostrarAlerta("El stock no puede ser negativo", Alert.AlertType.WARNING);
                return;
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("El stock debe ser un número válido", Alert.AlertType.ERROR);
            return;
        }

        if (unidadMedida.getValue() == null) {
            mostrarAlerta("Debe seleccionar una unidad", Alert.AlertType.WARNING);
            return;
        }

        if (productoSeleccionado == null) {
            Producto producto = new Producto();

            producto.setNombre(nombre.getText());
            producto.setStockMinimo(stock);
            producto.setUnidadMed(unidadMedida.getValue());

            productoServicio.guardar(producto);

            mostrarEstado("✔ Producto creado correctamente", false);

        } else {
            productoSeleccionado.setNombre(nombre.getText());
            productoSeleccionado.setStockMinimo(stock);
            productoSeleccionado.setUnidadMed(unidadMedida.getValue());

            productoServicio.guardar(productoSeleccionado);

            mostrarEstado("✔ Producto actualizado correctamente", false);

            productoSeleccionado = null;
        }

        limpiarFormulario();
    }

    @FXML
    public void irAVistaTabla() {
        navegacionService.cambiarVista("/vistas/productos-lista-view.fxml");
    }

    @FXML
    public void volver() {
        navegacionService.cambiarVista("/vistas/admin-view.fxml");
    }

    @FXML
    public void cargarProductoParaEditar(Producto producto) {
        productoSeleccionado = producto;

        nombre.setText(producto.getNombre());
        stockMinimo.setText(String.valueOf(producto.getStockMinimo()));
        unidadMedida.setValue(producto.getUnidadMed());
    }

    private void limpiarFormulario() {
        nombre.clear();
        stockMinimo.clear();
        unidadMedida.setValue(null);
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
}