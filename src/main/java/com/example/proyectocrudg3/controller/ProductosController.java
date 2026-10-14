package com.example.proyectocrudg3.controller;

import com.example.proyectocrudg3.model.Categoria;
import com.example.proyectocrudg3.model.Marca;
import com.example.proyectocrudg3.model.Producto;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProductosController implements Initializable {

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtStock;

    @FXML
    private ComboBox<Categoria> cmbCategoria;

    @FXML
    private ComboBox<Marca> cmbMarca;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TextField txtBuscarTexto;

    @FXML
    private ComboBox<Categoria> cmbFiltroCategoria;

    @FXML
    private DatePicker dpDesde;

    @FXML
    private DatePicker dpHasta;

    @FXML
    private TextField txtPrecioMin;

    @FXML
    private TextField txtPrecioMax;

    @FXML
    private CheckBox chkSoloExistencias;

    @FXML
    private ComboBox<String> cmbOrdenarPor;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnLimpiarFiltros;

    @FXML
    private TableView<Producto> tblProductos;

    @FXML
    private TableColumn<Producto, String> colCodigo;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, String> colCategoria;

    @FXML
    private TableColumn<Producto, String> colMarca;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    @FXML
    private TableColumn<Producto, Integer> colStock;

    @FXML
    private TableColumn<Producto, LocalDate> colFechaCreacion;

    private final ObservableList<Producto> listaProductos = FXCollections.observableArrayList();
    private final ObservableList<Categoria> listaCategorias = FXCollections.observableArrayList();
    private final ObservableList<Marca> listaMarcas = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColumnas();
        configurarSeleccion();

        cmbOrdenarPor.setItems(FXCollections.observableArrayList(
                "Nombre", "Precio menor a mayor", "Precio mayor a menor", "Fecha"
        ));
        cmbOrdenarPor.setValue("Nombre");
        dpFecha.setValue(LocalDate.now());

        cargarDatosEstaticos();
    }

    private void configurarColumnas() {
        colCodigo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCodigo()));
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colCategoria.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCategoria()));
        colMarca.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getMarca()));
        colPrecio.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getPrecio()).asObject());
        colStock.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getStock()).asObject());
        colFechaCreacion.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getFechaCreacion()));

        tblProductos.setItems(listaProductos);
    }

    private void configurarSeleccion() {
        tblProductos.getSelectionModel().selectedItemProperty().addListener((obs, viejo, nuevo) -> {
            if (nuevo != null) {
                txtCodigo.setText(nuevo.getCodigo());
                txtNombre.setText(nuevo.getNombre());
                txtPrecio.setText(String.valueOf(nuevo.getPrecio()));
                txtStock.setText(String.valueOf(nuevo.getStock()));
                txtDescripcion.setText(nuevo.getDescripcion() != null ? nuevo.getDescripcion() : "");
                dpFecha.setValue(nuevo.getFechaCreacion());

                for (Categoria cat : listaCategorias) {
                    if (cat.getIdCategoria() == nuevo.getIdCategoria()) {
                        cmbCategoria.setValue(cat);
                        break;
                    }
                }

                for (Marca m : listaMarcas) {
                    if (m.getIdMarca() == nuevo.getIdMarca()) {
                        cmbMarca.setValue(m);
                        break;
                    }
                }
            }
        });
    }

    private void cargarDatosEstaticos() {
        listaCategorias.setAll(
                new Categoria(1, "Electrónica"),
                new Categoria(2, "Línea Blanca"),
                new Categoria(3, "Ropa"),
                new Categoria(4, "Muebles"),
                new Categoria(5, "Alimentos")
        );
        cmbCategoria.setItems(listaCategorias);
        cmbFiltroCategoria.setItems(listaCategorias);

        listaMarcas.setAll(
                new Marca(1, "Samsung"),
                new Marca(2, "Sony"),
                new Marca(3, "LG"),
                new Marca(4, "Mabe"),
                new Marca(5, "Nike")
        );
        cmbMarca.setItems(listaMarcas);

        listaProductos.setAll(
                new Producto(1, "PROD-001", "Smart TV 55 pulgadas 4K", "Televisor inteligente con resolución UHD", 550.00, 15, LocalDate.now(), 1, "Electrónica", 1, "Samsung", 1),
                new Producto(2, "PROD-002", "Refrigeradora Inverter 14 pies", "Refrigeradora de dos puertas con ahorro de energía", 620.50, 10, LocalDate.now(), 2, "Línea Blanca", 4, "Mabe", 1),
                new Producto(3, "PROD-003", "Zapatos deportivos Air Zoom", "Zapatillas deportivas ligeras para maratón", 120.00, 30, LocalDate.now(), 3, "Ropa", 5, "Nike", 1),
                new Producto(4, "PROD-004", "PlayStation 5", "Consola de videojuegos de última generación", 499.99, 25, LocalDate.now(), 1, "Electrónica", 2, "Sony", 1),
                new Producto(5, "PROD-005", "Microondas 1.5 pies cúbicos", "Horno de microondas con 10 niveles de potencia", 115.00, 40, LocalDate.now(), 2, "Línea Blanca", 3, "LG", 1),
                new Producto(6, "PROD-006", "Smartphone Galaxy S24", "Celular de gama alta con 256GB de almacenamiento", 850.00, 20, LocalDate.now(), 1, "Electrónica", 1, "Samsung", 1),
                new Producto(7, "PROD-007", "Cámara Mirrorless Alpha 7", "Cámara profesional con lente intercambiable", 1200.00, 5, LocalDate.now(), 1, "Electrónica", 2, "Sony", 1),
                new Producto(8, "PROD-008", "Lavadora Carga Frontal 22kg", "Lavadora automática con tecnología Inverter", 450.00, 12, LocalDate.now(), 2, "Línea Blanca", 3, "LG", 1),
                new Producto(9, "PROD-009", "Camiseta Deportiva Dri-FIT", "Camiseta transpirable para entrenamiento", 25.00, 100, LocalDate.now(), 3, "Ropa", 5, "Nike", 1),
                new Producto(10, "PROD-010", "Cocina de Gas 6 Quemadores", "Cocina con horno amplio y encendido electrónico", 280.00, 8, LocalDate.now(), 2, "Línea Blanca", 4, "Mabe", 1),
                new Producto(11, "PROD-011", "Audífonos Inalámbricos Noise Cancelling", "Audífonos de diadema con cancelación de ruido", 350.00, 18, LocalDate.now(), 1, "Electrónica", 2, "Sony", 1),
                new Producto(12, "PROD-012", "Monitor Curvo 27 pulgadas", "Monitor ideal para gaming y productividad", 210.00, 22, LocalDate.now(), 1, "Electrónica", 1, "Samsung", 1),
                new Producto(13, "PROD-013", "Shorts de Entrenamiento", "Pantalones cortos de tela ligera y elástica", 35.00, 50, LocalDate.now(), 3, "Ropa", 5, "Nike", 1),
                new Producto(14, "PROD-014", "Aire Acondicionado 12000 BTU", "Minisplit con enfriamiento rápido y filtro purificador", 340.00, 15, LocalDate.now(), 2, "Línea Blanca", 3, "LG", 1),
                new Producto(15, "PROD-015", "Tablet Galaxy Tab S9", "Tablet de 11 pulgadas con S-Pen incluido", 650.00, 10, LocalDate.now(), 1, "Electrónica", 1, "Samsung", 1)
        );
    }

    @FXML
    private void onNuevo(ActionEvent event) {
        limpiarFormulario();
        tblProductos.getSelectionModel().clearSelection();
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        if (txtCodigo.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty() ||
                cmbCategoria.getValue() == null || cmbMarca.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "Completa código, nombre, categoría y marca.");
            return;
        }

        try {
            String codigo = txtCodigo.getText().trim();
            for (Producto p : listaProductos) {
                if (p.getCodigo().equalsIgnoreCase(codigo)) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ya existe un producto con el código: " + codigo);
                    return;
                }
            }

            Categoria cat = cmbCategoria.getValue();
            Marca mar = cmbMarca.getValue();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());
            LocalDate fecha = dpFecha.getValue() != null ? dpFecha.getValue() : LocalDate.now();

            Producto nuevo = new Producto(
                    listaProductos.size() + 1,
                    codigo,
                    txtNombre.getText().trim(),
                    txtDescripcion.getText().trim(),
                    precio,
                    stock,
                    fecha,
                    cat.getIdCategoria(),
                    cat.getNombre(),
                    mar.getIdMarca(),
                    mar.getNombre(),
                    1
            );

            listaProductos.add(nuevo);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Producto guardado correctamente en la lista.");
            limpiarFormulario();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Formato", "Precio y stock deben ser números válidos.");
        }
    }

    @FXML
    private void onActualizar(ActionEvent event) {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "Selecciona un producto para actualizar.");
            return;
        }

        Producto encontrado = null;
        for (Producto p : listaProductos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                encontrado = p;
                break;
            }
        }

        if (encontrado == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se encontró el producto a actualizar.");
            return;
        }

        try {
            encontrado.setNombre(txtNombre.getText().trim());
            encontrado.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
            encontrado.setStock(Integer.parseInt(txtStock.getText().trim()));
            encontrado.setDescripcion(txtDescripcion.getText().trim());
            encontrado.setFechaCreacion(dpFecha.getValue());

            if (cmbCategoria.getValue() != null) {
                encontrado.setIdCategoria(cmbCategoria.getValue().getIdCategoria());
                encontrado.setCategoria(cmbCategoria.getValue().getNombre());
            }

            if (cmbMarca.getValue() != null) {
                encontrado.setIdMarca(cmbMarca.getValue().getIdMarca());
                encontrado.setMarca(cmbMarca.getValue().getNombre());
            }

            tblProductos.refresh();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Producto actualizado correctamente.");
            limpiarFormulario();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Formato", "Precio y stock deben ser números válidos.");
        }
    }

    @FXML
    private void onEliminar(ActionEvent event) {
        Producto seleccionado = tblProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "Selecciona una fila de la tabla para eliminar.");
            return;
        }

        listaProductos.remove(seleccionado);
        mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Producto eliminado de la lista.");
        limpiarFormulario();
    }

    @FXML
    private void onBuscar(ActionEvent event) {
        String texto = txtBuscarTexto.getText().trim().toLowerCase();
        Categoria catFiltro = cmbFiltroCategoria.getValue();
        boolean soloExistencias = chkSoloExistencias.isSelected();

        ObservableList<Producto> filtrada = FXCollections.observableArrayList();

        for (Producto p : listaProductos) {
            boolean coincideTexto = texto.isEmpty() ||
                    p.getCodigo().toLowerCase().contains(texto) ||
                    p.getNombre().toLowerCase().contains(texto);

            boolean coincideCat = (catFiltro == null) || (p.getIdCategoria() == catFiltro.getIdCategoria());
            boolean coincideStock = !soloExistencias || (p.getStock() > 0);

            if (coincideTexto && coincideCat && coincideStock) {
                filtrada.add(p);
            }
        }

        tblProductos.setItems(filtrada);
    }

    @FXML
    private void onLimpiarFiltros(ActionEvent event) {
        txtBuscarTexto.clear();
        cmbFiltroCategoria.setValue(null);
        dpDesde.setValue(null);
        dpHasta.setValue(null);
        txtPrecioMin.clear();
        txtPrecioMax.clear();
        chkSoloExistencias.setSelected(false);
        cmbOrdenarPor.setValue("Nombre");

        tblProductos.setItems(listaProductos);
    }

    private void limpiarFormulario() {
        txtCodigo.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtStock.clear();
        cmbCategoria.setValue(null);
        cmbMarca.setValue(null);
        dpFecha.setValue(LocalDate.now());
        txtDescripcion.clear();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}