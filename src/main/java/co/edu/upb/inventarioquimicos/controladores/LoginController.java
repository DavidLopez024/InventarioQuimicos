package co.edu.upb.inventarioquimicos.controladores;

import co.edu.upb.inventarioquimicos.modelos.entidades.Usuario;
import co.edu.upb.inventarioquimicos.modelos.enums.Rol;
import co.edu.upb.inventarioquimicos.controladores.usuarioSesion;
import co.edu.upb.inventarioquimicos.servicios.interfaces.UsuarioServicio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Logger;

@Component
public class LoginController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    @FXML
    private TextField usuario;

    @FXML
    private PasswordField password;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NavegacionService navegacionService;

    @FXML
    public void login() {

        Optional<Usuario> user = usuarioServicio.buscarPorUsername(usuario.getText());

        if (user.isPresent() && passwordEncoder.matches(password.getText(), user.get().getPassword())) {

            usuarioSesion.setRol(user.get().getRol().name());
            usuarioSesion.setUsuario(user.get());

            if (user.get().getRol() == Rol.ADMINISTRADOR) {
                navegacionService.cambiarVista("/vistas/admin-view.fxml");
            } else if (user.get().getRol() == Rol.BODEGUERO) {
                navegacionService.cambiarVista("/vistas/bodeguero-view.fxml");
            }

        } else {
            mostrarAlerta("Usuario o contraseña incorrectos", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Mensaje");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}