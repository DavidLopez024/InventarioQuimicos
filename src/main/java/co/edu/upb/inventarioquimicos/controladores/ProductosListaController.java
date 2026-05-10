package co.edu.upb.inventarioquimicos.controladores;

import co.edu.upb.inventarioquimicos.modelos.entidades.Producto;
import co.edu.upb.inventarioquimicos.modelos.enums.UnidadMed;
import co.edu.upb.inventarioquimicos.servicios.interfaces.ProductoServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import co.edu.upb.inventarioquimicos.ApplicationContextProvider;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ProductosListaController {

    private static final Logger LOGGER = Logger.getLogger(ProductosListaController.class.getName());

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, Double> colStock;

    @FXML
    private TableColumn<Producto, UnidadMed> colUnidad;

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private NavegacionService navegacionService;

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stockMinimo"));
        colUnidad.setCellValueFactory(new PropertyValueFactory<>("unidadMed"));

        cargarProductos();
    }

    private void cargarProductos() {
        ObservableList<Producto> lista = FXCollections.observableArrayList(productoServicio.listarProductos());
        tablaProductos.setItems(lista);
    }

    @FXML
    public void volver() {
        navegacionService.cambiarVista("/vistas/producto-view.fxml");
    }

    @FXML
    public void editarProducto() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/producto-view.fxml"));

                loader.setControllerFactory(clazz ->
                        ApplicationContextProvider.getApplicationContext().getBean(clazz));

                loader.load();

                ProductoController controller = loader.getController();
                controller.cargarProductoParaEditar(seleccionado);

                Stage stage = (Stage) tablaProductos.getScene().getWindow();
                stage.getScene().setRoot(loader.getRoot());

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al cargar vista de producto", e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Debe seleccionar un producto primero");
            alert.showAndWait();
        }
    }

    @FXML
    public void eliminarProducto() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar");
            alert.setContentText("¿Eliminar producto?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                try {
                    productoServicio.eliminar(seleccionado.getId());
                    cargarProductos();
                } catch (DataIntegrityViolationException e) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText(null);
                    error.setContentText("No se puede eliminar el producto porque tiene lotes asociados.");
                    error.showAndWait();
                }
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Debe seleccionar un producto primero");
            alert.showAndWait();
        }
    }
}