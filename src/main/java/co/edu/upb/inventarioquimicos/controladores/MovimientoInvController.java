package co.edu.upb.inventarioquimicos.controladores;

import co.edu.upb.inventarioquimicos.modelos.entidades.MovimientoInv;
import co.edu.upb.inventarioquimicos.servicios.interfaces.MovimientoInvServicio;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovimientoInvController {

    @FXML
    private TableView<MovimientoInv> tablaMovimientos;

    @FXML
    private TableColumn<MovimientoInv, ?> colFecha;

    @FXML
    private TableColumn<MovimientoInv, ?> colTipo;

    @FXML
    private TableColumn<MovimientoInv, ?> colUnidad;

    @FXML
    private TableColumn<MovimientoInv, String> colObs;

    @FXML
    private TableColumn<MovimientoInv, String> colProducto;

    @FXML
    private TableColumn<MovimientoInv, String> colOrigen;

    @FXML
    private TableColumn<MovimientoInv, String> colDestino;

    @FXML
    private TableColumn<MovimientoInv, String> colUsuario;

    @Autowired
    private MovimientoInvServicio movimientoInvServicio;

    @Autowired
    private NavegacionService navegacionService;

    @FXML
    public void initialize() {

        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoMov"));
        colUnidad.setCellValueFactory(new PropertyValueFactory<>("unidadMed"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        colOrigen.setCellValueFactory(new PropertyValueFactory<>("origen"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        colObs.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        colUsuario.setCellValueFactory(data -> {
            var u = data.getValue().getUsuario();
            return new javafx.beans.property.SimpleStringProperty(
                    u != null ? u.getNombre() : "—");
        });

        tablaMovimientos.setItems(
                FXCollections.observableArrayList(
                        movimientoInvServicio.listarMovimientosInv()
                )
        );
    }

    @FXML
    public void irAProductos() {
        navegacionService.cambiarVista("/vistas/producto-view.fxml");
    }

    @FXML
    public void irAInventario() {
        navegacionService.cambiarVista("/vistas/inventario-view.fxml");
    }

    @FXML
    public void iraPanelAdministracion() {
        navegacionService.cambiarVista("/vistas/admin-view.fxml");
    }

    @FXML
    public void irAPanelBodeguero() {
        navegacionService.volverAPanel();
    }

    @FXML
    public void irATransladoDeProducto() {
        navegacionService.cambiarVista("/vistas/movimiento-traslado-view.fxml");
    }

    @FXML
    public void irARegistroPerdida() {
        navegacionService.cambiarVista("/vistas/perdida-lote-view.fxml");
    }

    @FXML
    public void irAListaProductos() {
        navegacionService.cambiarVista("/vistas/productos-lista-view.fxml");
    }

    @FXML
    public void irARegistroProveedores() {
        navegacionService.cambiarVista("/vistas/proveedor-view.fxml");
    }

    @FXML
    public void irARegistroLote() {
        navegacionService.cambiarVista("/vistas/registro-lote-view.fxml");
    }

    @FXML
    public void volver() {
        navegacionService.volverAPanel();
    }
}