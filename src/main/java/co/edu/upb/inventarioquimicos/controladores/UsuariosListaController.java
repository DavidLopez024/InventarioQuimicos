package co.edu.upb.inventarioquimicos.controladores;

import co.edu.upb.inventarioquimicos.modelos.entidades.Usuario;
import co.edu.upb.inventarioquimicos.servicios.interfaces.UsuarioServicio;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuariosListaController {

    @FXML private TableView<Usuario>             tablaUsuarios;
    @FXML private TableColumn<Usuario, String>   colNombre;
    @FXML private TableColumn<Usuario, String>   colUsername;
    @FXML private TableColumn<Usuario, String>   colRol;

    @Autowired private UsuarioServicio   usuarioServicio;
    @Autowired private NavegacionService navegacionService;

    @FXML
    public void initialize() {
        colNombre  .setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRol.setCellValueFactory(data -> {
            var rol = data.getValue().getRol();
            return new javafx.beans.property.SimpleStringProperty(
                    rol == null ? "—" : rol.getNombre());
        });
        cargarTabla();
    }

    private void cargarTabla() {
        tablaUsuarios.setItems(
                FXCollections.observableArrayList(usuarioServicio.listarUsuarios()));
    }

    @FXML
    public void eliminarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Seleccione un usuario de la lista", Alert.AlertType.WARNING);
            return;
        }

        // Prevent deleting the only admin
        long admins = tablaUsuarios.getItems().stream()
                .filter(u -> u.getRol() != null &&
                             "ADMINISTRADOR".equals(u.getRol().name()))
                .count();
        if ("ADMINISTRADOR".equals(seleccionado.getRol().name()) && admins <= 1) {
            mostrarAlerta("No se puede eliminar el único administrador del sistema",
                          Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar al usuario \"" + seleccionado.getUsername() + "\"?",
                ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.YES) {
                usuarioServicio.eliminar(seleccionado.getId());
                cargarTabla();
            }
        });
    }

    @FXML
    public void volver() {
        navegacionService.cambiarVista("/vistas/registro-usuario-view.fxml");
    }

    private void mostrarAlerta(String msg, Alert.AlertType tipo) {
        Alert a = new Alert(tipo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
