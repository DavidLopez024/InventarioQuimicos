package co.edu.upb.inventarioquimicos.controladores;

import co.edu.upb.inventarioquimicos.modelos.entidades.Lote;
import co.edu.upb.inventarioquimicos.modelos.entidades.MovimientoInv;
import co.edu.upb.inventarioquimicos.modelos.entidades.Producto;
import co.edu.upb.inventarioquimicos.modelos.enums.TipoMov;
import co.edu.upb.inventarioquimicos.servicios.interfaces.LoteServicio;
import co.edu.upb.inventarioquimicos.servicios.interfaces.MovimientoInvServicio;
import co.edu.upb.inventarioquimicos.servicios.interfaces.ProductoServicio;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.logging.Logger;

@Component
public class LoteController {

    private static final Logger LOGGER = Logger.getLogger(LoteController.class.getName());

    @FXML private ComboBox<Producto> productoCombo;
    @FXML private TextField          numeroLote;
    @FXML private TextField          cantidad;
    @FXML private TextField          costo;
    @FXML private TextField          fechaIngreso;
    @FXML private DatePicker         fechaVencimiento;
    @FXML private Label              statusLabel;

    @Autowired private ProductoServicio      productoServicio;
    @Autowired private LoteServicio          loteServicio;
    @Autowired private MovimientoInvServicio movimientoInvServicio;
    @Autowired private NavegacionService     navegacionService;

    @FXML
    public void initialize() {
        productoCombo.getItems().setAll(productoServicio.listarProductos());
        fechaIngreso.setText(LocalDate.now().toString());

        // Live feedback: check uniqueness as the user types
        numeroLote.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isBlank()) {
                statusLabel.setText("");
                return;
            }
            boolean existe = loteServicio.buscarPorNumeroLote(newVal.trim()).isPresent();
            if (existe) {
                mostrarEstado("⚠ El número de lote ya existe", true);
            } else {
                mostrarEstado("✔ Número de lote disponible", false);
            }
        });
    }

    @FXML
    public void guardarLote() {

        if (productoCombo.getValue() == null) {
            mostrarAlerta("Seleccione un producto", Alert.AlertType.WARNING);
            return;
        }

        String numLote = numeroLote.getText().trim();
        if (numLote.isEmpty()) {
            mostrarAlerta("Ingrese el número de lote", Alert.AlertType.WARNING);
            return;
        }

        if (loteServicio.buscarPorNumeroLote(numLote).isPresent()) {
            mostrarAlerta("El número de lote \"" + numLote + "\" ya existe. Use uno diferente.",
                          Alert.AlertType.ERROR);
            return;
        }

        if (fechaVencimiento.getValue() == null) {
            mostrarAlerta("Seleccione la fecha de vencimiento", Alert.AlertType.WARNING);
            return;
        }
        if (!fechaVencimiento.getValue().isAfter(LocalDate.now())) {
            mostrarAlerta("La fecha de vencimiento debe ser futura", Alert.AlertType.WARNING);
            return;
        }

        double cantidadValor;
        double costoValor;

        try {
            cantidadValor = Double.parseDouble(cantidad.getText());
            if (cantidadValor <= 0) {
                mostrarAlerta("La cantidad debe ser mayor a 0", Alert.AlertType.WARNING);
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("La cantidad debe ser un número válido", Alert.AlertType.ERROR);
            return;
        }

        try {
            costoValor = Double.parseDouble(costo.getText());
            if (costoValor < 0) {
                mostrarAlerta("El costo no puede ser negativo", Alert.AlertType.WARNING);
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("El costo debe ser un número válido", Alert.AlertType.ERROR);
            return;
        }

        Lote lote = new Lote();
        lote.setProducto(productoCombo.getValue());
        lote.setNumeroLote(numLote);
        lote.setFechaIngreso(LocalDate.now());
        lote.setFechaVencimiento(fechaVencimiento.getValue());
        lote.setCantidad(cantidadValor);
        lote.setCosto(costoValor);

        loteServicio.guardar(lote);

        Producto producto = productoCombo.getValue();
        MovimientoInv mov = new MovimientoInv();
        mov.setFecha(LocalDate.now());
        mov.setTipoMov(TipoMov.ENTRADA);
        mov.setUnidadMed(producto.getUnidadMed());
        mov.setProducto(producto.getNombre());
        mov.setOrigen("PROVEEDOR");
        mov.setDestino("INVENTARIO");
        mov.setObservaciones("Ingreso lote " + numLote);
        mov.setUsuario(usuarioSesion.getUsuario());

        movimientoInvServicio.guardar(mov);

        mostrarEstado("✔ Entrada registrada — Lote " + numLote, false);
        limpiarCampos();
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

    @FXML
    public void volver() {
        navegacionService.volverAPanel();
    }

    private void limpiarCampos() {
        productoCombo.setValue(null);
        numeroLote.clear();
        cantidad.clear();
        costo.clear();
        fechaVencimiento.setValue(null);
        fechaIngreso.setText(LocalDate.now().toString());
    }
}
