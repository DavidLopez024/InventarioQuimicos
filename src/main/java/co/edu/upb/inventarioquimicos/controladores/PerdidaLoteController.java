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
import java.util.List;
import java.util.logging.Logger;

@Component
public class PerdidaLoteController {

    private static final Logger LOGGER = Logger.getLogger(PerdidaLoteController.class.getName());

    @FXML
    private ComboBox<Producto> productoCombo;

    @FXML
    private ComboBox<Lote> loteCombo;

    @FXML
    private TextField cantidad;

    @FXML
    private Label statusLabel;

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private LoteServicio loteServicio;

    @Autowired
    private MovimientoInvServicio movimientoInvServicio;

    @Autowired
    private NavegacionService navegacionService;

    @FXML
    public void initialize() {
        productoCombo.getItems().setAll(productoServicio.listarProductos());
        productoCombo.setOnAction(event -> cargarLotesPorProducto());
    }

    private void cargarLotesPorProducto() {
        Producto productoSeleccionado = productoCombo.getValue();

        if (productoSeleccionado != null) {
            List<Lote> lotesDelProducto = loteServicio.buscarPorProductoIdConStock(productoSeleccionado.getId());
            loteCombo.getItems().setAll(lotesDelProducto);
        }
    }

    @FXML
    public void registrarPerdida() {

        if (productoCombo.getValue() == null || loteCombo.getValue() == null) {
            mostrarAlerta("Seleccione producto y lote", Alert.AlertType.WARNING);
            return;
        }

        double cantidadPerdida;

        try {
            cantidadPerdida = Double.parseDouble(cantidad.getText());

            if (cantidadPerdida <= 0) {
                mostrarAlerta("La cantidad debe ser mayor a 0", Alert.AlertType.WARNING);
                return;
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Ingrese un número válido", Alert.AlertType.ERROR);
            return;
        }

        Lote loteSeleccionado = loteCombo.getValue();

        if (cantidadPerdida > loteSeleccionado.getCantidad()) {
            mostrarAlerta("La pérdida no puede ser mayor a la cantidad disponible del lote", Alert.AlertType.WARNING);
            return;
        }

        if (loteSeleccionado.getProducto() == null) {
            mostrarAlerta("El lote seleccionado no tiene un producto asociado", Alert.AlertType.ERROR);
            return;
        }

        Producto producto = loteSeleccionado.getProducto();
        loteSeleccionado.setCantidad(loteSeleccionado.getCantidad() - cantidadPerdida);
        loteServicio.guardar(loteSeleccionado);

        if (loteSeleccionado.getCantidad() <= 0) {
            loteServicio.eliminar(loteSeleccionado.getId());
        }

        MovimientoInv mov = new MovimientoInv();
        mov.setFecha(LocalDate.now());
        mov.setTipoMov(TipoMov.PERDIDA);
        mov.setUnidadMed(producto.getUnidadMed());

        mov.setProducto(producto.getNombre());
        mov.setOrigen("INVENTARIO");
        mov.setDestino("PERDIDA");
        mov.setObservaciones("Pérdida en lote " +
                (loteSeleccionado.getNumeroLote() != null
                        ? loteSeleccionado.getNumeroLote()
                        : "#" + loteSeleccionado.getId()));
        mov.setUsuario(usuarioSesion.getUsuario());

        movimientoInvServicio.guardar(mov);

        mostrarEstado("✔ Pérdida registrada correctamente", false);

        cantidad.clear();
        productoCombo.setValue(null);
        loteCombo.getItems().clear();
        loteCombo.setValue(null);
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
}