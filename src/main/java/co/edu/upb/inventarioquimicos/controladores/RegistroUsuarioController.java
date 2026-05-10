package co.edu.upb.inventarioquimicos.controladores;

import co.edu.upb.inventarioquimicos.modelos.entidades.Usuario;
import co.edu.upb.inventarioquimicos.modelos.enums.Rol;
import co.edu.upb.inventarioquimicos.servicios.interfaces.UsuarioServicio;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistroUsuarioController {

    @FXML private TextField    nombreCompleto;
    @FXML private TextField    username;
    @FXML private PasswordField password;
    @FXML private PasswordField confirmPassword;
    @FXML private ComboBox<Rol> rolCombo;
    @FXML private Label         statusLabel;

    @Autowired private UsuarioServicio  usuarioServicio;
    @Autowired private NavegacionService navegacionService;

    @FXML
    public void initialize() {
        rolCombo.getItems().addAll(Rol.values());
        rolCombo.setConverter(new StringConverter<>() {
            @Override public String toString(Rol rol)   { return rol == null ? "" : rol.getNombre(); }
            @Override public Rol fromString(String s)   { return null; }
        });
    }

    @FXML
    public void guardarUsuario() {
        String nombre   = nombreCompleto.getText().trim();
        String user     = username.getText().trim();
        String pass     = password.getText();
        String confirm  = confirmPassword.getText();
        Rol    rol      = rolCombo.getValue();

        if (nombre.isEmpty() || user.isEmpty() || pass.isEmpty() || rol == null) {
            mostrarEstado("Complete todos los campos obligatorios", true);
            return;
        }
        if (!pass.equals(confirm)) {
            mostrarEstado("Las contraseñas no coinciden", true);
            return;
        }
        if (pass.length() < 4) {
            mostrarEstado("La contraseña debe tener al menos 4 caracteres", true);
            return;
        }
        if (usuarioServicio.buscarPorUsername(user).isPresent()) {
            mostrarEstado("El nombre de usuario ya existe", true);
            return;
        }

        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setUsername(user);
        u.setPassword(pass);
        u.setRol(rol);
        usuarioServicio.guardar(u);

        mostrarEstado("✔ Usuario registrado correctamente", false);
        nombreCompleto.clear();
        username.clear();
        password.clear();
        confirmPassword.clear();
        rolCombo.setValue(null);
    }

    @FXML
    public void irAListaUsuarios() {
        navegacionService.cambiarVista("/vistas/usuarios-lista-view.fxml");
    }

    @FXML
    public void volver() {
        navegacionService.volverAPanel();
    }

    private void mostrarEstado(String msg, boolean esError) {
        statusLabel.setText(msg);
        statusLabel.setStyle(esError
                ? "-fx-text-fill: #c62828; -fx-font-size: 13px; -fx-font-weight: bold;"
                : "-fx-text-fill: #2e7d32; -fx-font-size: 13px; -fx-font-weight: bold;");
    }
}
