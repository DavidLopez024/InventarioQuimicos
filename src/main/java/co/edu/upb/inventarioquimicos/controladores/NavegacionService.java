package co.edu.upb.inventarioquimicos.controladores;

import co.edu.upb.inventarioquimicos.ApplicationContextProvider;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class NavegacionService {

    private static final Logger LOGGER = Logger.getLogger(NavegacionService.class.getName());

    /** Regresa al panel del usuario autenticado (admin o bodeguero). */
    public void volverAPanel() {
        String rol = usuarioSesion.getRol();
        if ("ADMINISTRADOR".equals(rol)) {
            cambiarVista("/vistas/admin-view.fxml");
        } else {
            cambiarVista("/vistas/bodeguero-view.fxml");
        }
    }

    public void cambiarVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));

            loader.setControllerFactory(clazz ->
                    ApplicationContextProvider.getApplicationContext().getBean(clazz));

            Parent root = loader.load();

            Stage stage = Window.getWindows().stream()
                    .filter(Window::isShowing)
                    .filter(w -> w instanceof Stage)
                    .map(w -> (Stage) w)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No hay ventana principal abierta"));
            stage.getScene().setRoot(root);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cargar vista: " + rutaFXML, e);
        }
    }
}