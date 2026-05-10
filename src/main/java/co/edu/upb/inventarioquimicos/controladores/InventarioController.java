package co.edu.upb.inventarioquimicos.controladores;

import co.edu.upb.inventarioquimicos.modelos.entidades.Lote;
import co.edu.upb.inventarioquimicos.servicios.interfaces.LoteServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

@Component
public class InventarioController {

    private static final Logger LOGGER = Logger.getLogger(InventarioController.class.getName());

    @FXML
    private TableView<Lote> tablaInventario;

    @FXML
    private TableColumn<Lote, String> colProducto;

    @FXML
    private TableColumn<Lote, String> colProveedor;

    @FXML
    private TableColumn<Lote, String> colFecha;

    @FXML
    private TableColumn<Lote, String> colVencimiento;

    @FXML
    private TableColumn<Lote, Double> colCosto;

    @FXML
    private TableColumn<Lote, Double> colCantidad;

    @FXML
    private ListView<String> listaStockTotal;

    @FXML
    private ListView<String> listaAlertas;

    @Autowired
    private LoteServicio loteServicio;

    @Autowired
    private NavegacionService navegacionService;

    @FXML
    public void initialize() {

        colProducto.setCellValueFactory(data -> {
            Lote lote = data.getValue();
            String nombre = (lote.getProducto() != null) ? lote.getProducto().getNombre() : "—";
            return new javafx.beans.property.SimpleStringProperty(nombre);
        });

        colProveedor.setCellValueFactory(data -> {
            Lote lote = data.getValue();
            String nombre = (lote.getProveedor() != null) ? lote.getProveedor().getNombre() : "—";
            return new javafx.beans.property.SimpleStringProperty(nombre);
        });

        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaIngreso"));
        colVencimiento.setCellValueFactory(new PropertyValueFactory<>("fechaVencimiento"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        // Carga única — se reutiliza en tabla, resumen y alertas
        java.util.List<Lote> lotes = loteServicio.listarLotes();

        cargarDatos(lotes);
        cargarResumenStock(lotes);
        cargarAlertas(lotes);
    }

    private void cargarDatos(java.util.List<Lote> lotes) {
        tablaInventario.setItems(FXCollections.observableArrayList(lotes));
    }

    private void cargarResumenStock(java.util.List<Lote> lotes) {
        Map<String, Double> stockPorProducto = new HashMap<>();

        for (Lote lote : lotes) {
            if (lote.getProducto() == null) continue;
            String nombreProducto = lote.getProducto().getNombre();
            stockPorProducto.merge(nombreProducto, lote.getCantidad(), Double::sum);
        }

        listaStockTotal.getItems().clear();
        for (Map.Entry<String, Double> entry : stockPorProducto.entrySet()) {
            listaStockTotal.getItems().add(entry.getKey() + " → " + entry.getValue());
        }
    }

    @FXML
    public void volver() {
        navegacionService.volverAPanel();
    }

    private void cargarAlertas(java.util.List<Lote> lotes) {
        Map<String, Double> stockPorProducto    = new HashMap<>();
        Map<String, Double> stockMinimoPorProducto = new HashMap<>();

        LocalDate hoy = LocalDate.now();
        listaAlertas.getItems().clear();

        for (Lote lote : lotes) {
            if (lote.getProducto() == null) continue;

            String nombreProducto = lote.getProducto().getNombre();

            // Acumular stock para comparar con mínimo
            stockPorProducto.merge(nombreProducto, lote.getCantidad(), Double::sum);
            stockMinimoPorProducto.put(nombreProducto, lote.getProducto().getStockMinimo());

            // Alertas de vencimiento
            if (lote.getFechaVencimiento() != null) {
                long dias = ChronoUnit.DAYS.between(hoy, lote.getFechaVencimiento());
                if (dias < 0) {
                    listaAlertas.getItems().add(
                            "⚠ Lote #" + lote.getId() + " de " + nombreProducto + " está VENCIDO");
                } else if (dias <= 7) {
                    listaAlertas.getItems().add(
                            "⚠ Lote #" + lote.getId() + " de " + nombreProducto + " vence en " + dias + " día(s)");
                }
            }
        }

        // Alertas de stock bajo
        for (String producto : stockPorProducto.keySet()) {
            double stock  = stockPorProducto.get(producto);
            double minimo = stockMinimoPorProducto.get(producto);
            if (stock < minimo) {
                listaAlertas.getItems().add(
                        "⚠ " + producto + " por debajo del stock mínimo (" + stock + " < " + minimo + ")");
            }
        }
    }
}