package co.edu.upb.inventarioquimicos.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

@Component
public class menuController {

    private final NavegacionService navegacionService;

    // ── Bodeguero ──
    @FXML private Button btnEntrada;
    @FXML private Button btnInventario;
    @FXML private Button btnPerdida;
    @FXML private Button btnTraslado;
    @FXML private Button btnMovimientos;

    // ── Solo admin ──
    @FXML private Button btnProductos;
    @FXML private Button btnProveedores;

    public menuController(NavegacionService navegacionService) {
        this.navegacionService = navegacionService;
    }

    @FXML
    public void initialize() {
        aplicarPermisosPorRol();
    }

    private void aplicarPermisosPorRol() {
        String rol = usuarioSesion.getRol();

        boolean esAdmin = "ADMINISTRADOR".equals(rol);

        // Los botones de admin sólo son visibles y ocupan espacio para ADMINISTRADOR
        setVisible(btnProductos,   esAdmin);
        setVisible(btnProveedores, esAdmin);
    }

    /** Oculta el botón Y le quita el espacio en el layout. */
    private void setVisible(Button btn, boolean visible) {
        btn.setVisible(visible);
        btn.setManaged(visible);
    }

    // ── Acciones ──
    @FXML private void irEntrada()     { navegacionService.cambiarVista("/vistas/registro-lote-view.fxml"); }
    @FXML private void irInventario()  { navegacionService.cambiarVista("/vistas/inventario-view.fxml"); }
    @FXML private void irPerdida()     { navegacionService.cambiarVista("/vistas/perdida-lote-view.fxml"); }
    @FXML private void irTraslado()    { navegacionService.cambiarVista("/vistas/movimiento-traslado-view.fxml"); }
    @FXML private void irMovimientos() { navegacionService.cambiarVista("/vistas/movimientos-view.fxml"); }
    @FXML private void irProductos()   { navegacionService.cambiarVista("/vistas/producto-view.fxml"); }
    @FXML private void irProveedores() { navegacionService.cambiarVista("/vistas/proveedor-view.fxml"); }
}
