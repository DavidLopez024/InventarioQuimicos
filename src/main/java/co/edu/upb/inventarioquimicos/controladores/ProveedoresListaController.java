package co.edu.upb.inventarioquimicos.controladores;

import co.edu.upb.inventarioquimicos.modelos.entidades.Proveedor;
import co.edu.upb.inventarioquimicos.servicios.interfaces.ProveedorServicio;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class ProveedoresListaController {

    @FXML private TableView<Proveedor>           tablaProveedores;
    @FXML private TableColumn<Proveedor, String> colNombre;
    @FXML private TableColumn<Proveedor, String> colContacto;
    @FXML private TableColumn<Proveedor, String> colTelefono;
    @FXML private TableColumn<Proveedor, String> colCorreo;
    @FXML private TableColumn<Proveedor, String> colFormaPago;

    @Autowired private ProveedorServicio  proveedorServicio;
    @Autowired private NavegacionService  navegacionService;

    @FXML
    public void initialize() {
        colNombre   .setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colContacto .setCellValueFactory(new PropertyValueFactory<>("contacto"));
        colTelefono .setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo   .setCellValueFactory(new PropertyValueFactory<>("correo"));
        colFormaPago.setCellValueFactory(new PropertyValueFactory<>("formaPago"));
        cargarTabla();
    }

    private void cargarTabla() {
        tablaProveedores.setItems(
                FXCollections.observableArrayList(proveedorServicio.listarProveedores()));
    }

    @FXML
    public void eliminarProveedor() {
        Proveedor seleccionado = tablaProveedores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Seleccione un proveedor de la lista", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar al proveedor \"" + seleccionado.getNombre() + "\"?",
                ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.YES) {
                try {
                    proveedorServicio.eliminar(seleccionado.getId());
                    cargarTabla();
                } catch (DataIntegrityViolationException e) {
                    mostrarAlerta(
                        "No se puede eliminar \"" + seleccionado.getNombre() +
                        "\" porque tiene lotes asociados en el inventario.",
                        Alert.AlertType.ERROR);
                }
            }
        });
    }

    @FXML
    public void volver() {
        navegacionService.volverAPanel();
    }

    private void mostrarAlerta(String msg, Alert.AlertType tipo) {
        Alert a = new Alert(tipo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
