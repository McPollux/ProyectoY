/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logintemp;

import Objetos.*;
import Recursos.Windows;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.hibernate.Session;

/**
 *
 * @author jordi
 */
public class ControladorCesta implements Initializable {

    private final Windows win = new Windows();

    @FXML
    private MenuBar menubarTienda;
    @FXML
    private Pane panel;

    @FXML
    private AnchorPane apDatos;

    @FXML
    private JFXRadioButton rbTrans;

    @FXML
    private ToggleGroup rgTipoPago;

    @FXML
    private JFXRadioButton rbTarj;

    @FXML
    private JFXComboBox<Object> cmbPago;

    @FXML
    private Label lblQuejas;

    @FXML
    private VBox vbProductos;

    @FXML
    private Label txtImporteTotal;
    @FXML
    private Menu miNombre;

    @FXML
    public void salir() {
        byte b = win.dialogo("¿Deseas Salir?");
        if (b == -1) {
            System.exit(0);
        }
    }

    @FXML
    public void cerrarSesion() throws IOException {

        byte b = win.dialogo("�Deseas cerrar sesi�n?");
        if (b == -1) {
            win.abrirVentana("/logintemp/FXMLDocument.fxml");
            win.cerrarVentana(rbTrans);
        }
    }

    @FXML
    public void miBanco() throws IOException {
        win.abrirVentana("/logintemp/MiBanco.fxml");
        win.cerrarVentana(rbTrans);
    }

    @FXML
    public void cuenta() throws IOException {
        win.abrirVentana("/logintemp/Perfil.fxml");
        win.cerrarVentana(rbTrans);
    }

    @FXML
    public void pedidosRealizados() throws IOException {
        win.abrirVentana("/logintemp/pedidos.fxml");
        win.cerrarVentana(rbTrans);
    }

    @FXML
    public void cesta() throws IOException {
        win.abrirVentana("/logintemp/cesta.fxml");
        win.cerrarVentana(rbTrans);
    }

    @FXML
    public void tienda() throws IOException {
        win.abrirVentana("/logintemp/tienda.fxml");
        win.cerrarVentana(rbTrans);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        miNombre.setText(LoginTemp.getClienteActual().getNombre());
        cargarCuentas();
        txtImporteTotal.setText("0");

        if (LoginTemp.cesta.getPedidos() != null) {
            MoverVentanas(panel);
            for (Pedidos i : LoginTemp.cesta.getPedidos()) {
                try {
                    ImageView imgDefinitive = null;

                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(i.getProducto().getImg()));
                    Image imgProd = SwingFXUtils.toFXImage(img, null);
                    imgDefinitive = new ImageView(imgProd);

                    anhadirProducto(i.getProducto().getNombre(), i.getProducto().getPrecio(), i.getCantidad(), imgDefinitive);
                } catch (IOException ex) {

                }
            }
        }
    }
        private void MoverVentanas(Pane root) {

        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);

        root.setOnMousePressed(e -> {
            Stage stage = (Stage) root.getScene().getWindow();
            xOffset.set(stage.getX() - e.getScreenX());
            yOffset.set(stage.getY() - e.getScreenY());

        });

        root.setOnMouseDragged(e -> {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setX(e.getScreenX() + xOffset.get());
            stage.setY(e.getScreenY() + yOffset.get());
        });
    }
    public void cargarCuentas() {
        cmbPago.getItems().clear();
        cmbPago.setPromptText("Elija su cuenta");
        Session s = NewHibernateUtil.getSession();
        Clientes cli = (Clientes) s.get(Clientes.class, LoginTemp.getClienteActual().getId());

        for (Cuentas i : cli.getCuentas()) {
            cmbPago.getItems().add(i.getNumeroCuenta());

        }
        s.close();
    }

    public void cargarTarjetas() {
        cmbPago.getItems().clear();
        Session s = NewHibernateUtil.getSession();
        Clientes cli = (Clientes) s.get(Clientes.class, LoginTemp.getClienteActual().getId());
        cmbPago.setPromptText("Elija su tarjeta");

        for (Cuentas i : cli.getCuentas()) {
            cmbPago.getItems().add(i.getTarjeta().getNumeroTarjeta());
        }
        s.close();

    }

    public void anhadirProducto(String nombre, float precio, int uni, ImageView imgDefinitive) throws IOException {

        vbProductos.setSpacing(10);
        HBox fila = new HBox();
        fila.setStyle("-fx-border-color: black;");
        fila.setSpacing(0);
        fila.setAlignment(Pos.CENTER);
        fila.setPrefSize(740, 140);

        // Campos de la columna "1", dicha columna almacena el campo nombre, el de precio unitario y el del total.
        VBox columna1 = new VBox();
        columna1.setPrefSize(150, 130);
        fila.getChildren().add(imgDefinitive);
        columna1.setSpacing(15);
        Label name = new Label(nombre);
        name.setPrefSize(150, 65);
        name.setAlignment(Pos.BOTTOM_CENTER);
        columna1.getChildren().add(name);

        //Separamos el label precio en 3
        HBox hbPrecio = new HBox();
        hbPrecio.setAlignment(Pos.TOP_CENTER);
        Label price1 = new Label("Precio: ");
        hbPrecio.getChildren().add(price1);

        Label price = new Label(String.valueOf(precio));
        hbPrecio.getChildren().add(price);

        Label price3 = new Label("€");
        hbPrecio.getChildren().add(price3);
        columna1.getChildren().add(hbPrecio);

        //Separamos el label precio total en 3
        HBox hbPrecioTotal = new HBox();
        hbPrecioTotal.setAlignment(Pos.BOTTOM_CENTER);
        Label totalPrice1 = new Label("Precio total: ");
        hbPrecioTotal.getChildren().add(totalPrice1);

        Label totalPrice = new Label(String.valueOf(precio * uni));
        hbPrecioTotal.getChildren().add(totalPrice);

        Label totalPrice3 = new Label("€");
        hbPrecioTotal.getChildren().add(totalPrice3);
        columna1.getChildren().add(hbPrecioTotal);

        fila.getChildren().add(columna1);
        //description.setStyle("-fx-border-color: black;");

        //Campos de la columna "2", almacena 3 filas: primera con el label x, otra con el label Unidades y TextField para la cantidad y la ultima
        //debajo con 2 botones, "+" y "-".
        VBox columna2 = new VBox();
        columna2.setPrefSize(210, 130);

        //Imagen papelera
        ImageView imgPap = new ImageView("/fotos/basura.jpg");
        HBox filaPapelera = new HBox();

        Label equis = new Label("");
        equis.setPrefSize(194, 10);
        imgPap.setOnMouseClicked((event) -> this.borrar(vbProductos, fila, nombre, totalPrice));
        filaPapelera.getChildren().add(equis);
        filaPapelera.getChildren().add(imgPap);

        columna2.getChildren().add(filaPapelera);

        //Fila con las unidades y TextField
        HBox filaUnidades = new HBox();
        filaUnidades.setSpacing(0);
        filaUnidades.setAlignment(Pos.CENTER);
        filaUnidades.setPrefSize(150, 85);

        Label unidades = new Label("Unidades:");
        unidades.setPrefSize(60, 85);
        filaUnidades.getChildren().add(unidades);

        TextField txtUni = new TextField();
        txtUni.setPrefSize(35, 20);
        txtUni.setText(String.valueOf(uni));
        txtUni.setOnKeyPressed((event) -> this.setUnidades(event, txtUni, totalPrice, price, nombre));
        filaUnidades.getChildren().add(txtUni);

        columna2.getChildren().add(filaUnidades);

        //Fila con los botones
        HBox filaBotones = new HBox();
        filaBotones.setSpacing(10);
        filaBotones.setAlignment(Pos.TOP_CENTER);
        filaBotones.setPrefSize(150, 45);

        ImageView imgPlus = new ImageView("/fotos/btnPlus1.png");
        imgPlus.setOnMouseClicked((event) -> this.sumar(txtUni, totalPrice, price, nombre));
        imgPlus.setOnMousePressed((event) -> this.cabiarImgSum(imgPlus));
        imgPlus.setOnMouseReleased((event) -> this.volverSum(imgPlus));
        filaBotones.getChildren().add(imgPlus);

        ImageView imgMinus = new ImageView("/fotos/btnMinus1.png");
        imgMinus.setOnMouseClicked((event) -> this.restar(txtUni, totalPrice, price, nombre));
        imgMinus.setOnMousePressed((event) -> this.cabiarImgRes(imgMinus));
        imgMinus.setOnMouseReleased((event) -> this.volverRes(imgMinus));
        filaBotones.getChildren().add(imgMinus);
        filaBotones.setSpacing(20);
        columna2.getChildren().add(filaBotones);

        //Introducimos toda la columna2 en la fila del pedido
        fila.getChildren().add(columna2);

        //Introducimos toda la fila en el VBox de productos y actualizamos el importe total
        vbProductos.getChildren().add(fila);

        actualizarImporteTotal(precio, uni);
    }

    public void sumar(TextField txtUni, Label totalPrice, Label price, String nombre) {
        Pedidos pedido = null;
        Set<Pedidos> listaPedidos = LoginTemp.cesta.getPedidos();
        for (Pedidos pe : listaPedidos) {
            if (pe.getProducto().getNombre().equals(nombre)) {
                pedido = pe;
                break;
            }
        }
        if (pedido == null) {
            System.out.println("Algo ha ido muy mal");
        } else {
            if (pedido.getProducto().getStockActual() >= Integer.parseInt(txtUni.getText()) + 1) {
                txtUni.setText(String.valueOf(Integer.parseInt(txtUni.getText()) + 1));
                Float nuevoPrecioTotal = Float.parseFloat(totalPrice.getText()) + Float.parseFloat(price.getText());
                totalPrice.setText(String.valueOf(nuevoPrecioTotal));
                pedido.setCantidad(pedido.getCantidad() + 1);
                pedido.setPrecioTotal(pedido.getPrecioTotal() + pedido.getProducto().getPrecio());
            } else {
                lblQuejas.setText("No hay suficientes existencias del producto " + nombre + " para aumentar el pedido");
            }
            txtImporteTotal.setText(String.valueOf(Float.parseFloat(txtImporteTotal.getText()) + Float.parseFloat(price.getText())));
        }
    }

    public void cabiarImgSum(ImageView imgReal) {
        Image img = new Image("/fotos/btnPlus2.png");
        imgReal.setImage(img);
    }

    public void volverSum(ImageView imgReal) {
        Image img = new Image("/fotos/btnPlus1.png");
        imgReal.setImage(img);
    }

    public void cabiarImgRes(ImageView imgReal) {
        Image img = new Image("/fotos/btnMinus2.png");
        imgReal.setImage(img);
    }

    public void volverRes(ImageView imgReal) {
        Image img = new Image("/fotos/btnMinus1.png");
        imgReal.setImage(img);
    }

    public void restar(TextField txtUni, Label totalPrice, Label price, String nombre) {

        if (!txtUni.getText().equalsIgnoreCase("1")) {
            Pedidos pedido = null;
            Set<Pedidos> listaPedidos = LoginTemp.cesta.getPedidos();
            for (Pedidos pe : listaPedidos) {
                if (pe.getProducto().getNombre().equals(nombre)) {
                    pedido = pe;
                    break;
                }
            }
            if (pedido == null) {
                System.out.println("Algo ha ido muy mal");
            } else {
                txtUni.setText(String.valueOf(Integer.parseInt(txtUni.getText()) - 1));
                Float nuevoPrecioTotal = Float.parseFloat(totalPrice.getText()) - Float.parseFloat(price.getText());
                totalPrice.setText(String.valueOf(nuevoPrecioTotal));
                pedido.setCantidad(pedido.getCantidad() - 1);
                pedido.setPrecioTotal(pedido.getPrecioTotal() - pedido.getProducto().getPrecio());

                txtImporteTotal.setText(String.valueOf(Float.parseFloat(txtImporteTotal.getText()) - Float.parseFloat(price.getText())));
            }
        } else {
            lblQuejas.setText("Mínimo un producto");
        }
    }

    public void setUnidades(Event evt, TextField txtUni, Label totalPrice, Label price, String nombre) {
        KeyEvent e = (KeyEvent) evt;
        byte b = 0;
        Pedidos pedido = null;
        float nuevoPrecioTotal = 0f;
        if (e.getCode() == KeyCode.ENTER) {
            try {
                if (!txtUni.getText().equals("")) {
                    b = 1;
                    if (Integer.parseInt(txtUni.getText()) > 0) {
                        b = 2;
                        Set<Pedidos> listaPedidos = LoginTemp.cesta.getPedidos();
                        for (Pedidos pe : listaPedidos) {
                            if (pe.getProducto().getNombre().equals(nombre)) {
                                pedido = pe;
                                break;
                            }
                        }
                        System.out.println(pedido.getCantidad());
                        if (pedido == null) {
                            System.out.println("Algo ha ido muy mal");
                        } else {
                            if (pedido.getProducto().getStockActual() >= Integer.parseInt(txtUni.getText())) {
                                txtUni.setText(String.valueOf(Integer.parseInt(txtUni.getText())));
                                nuevoPrecioTotal = Float.parseFloat(txtUni.getText()) * Float.parseFloat(price.getText());
                                totalPrice.setText(String.valueOf(nuevoPrecioTotal));
                                pedido.setCantidad(Integer.parseInt(txtUni.getText()));
                                pedido.setPrecioTotal(pedido.getCantidad() * pedido.getProducto().getPrecio());
                            } else {
                                lblQuejas.setText("No hay suficientes existencias del producto " + nombre + " para aumentar el pedido");
                                txtUni.setText(String.valueOf(pedido.getProducto().getStockActual()));
                            }
                            txtImporteTotal.setText(String.valueOf(Float.parseFloat(txtUni.getText()) * Float.parseFloat(price.getText())));
                        }
                    }
                }
                if (b != 2) {
                    Set<Pedidos> listaPedidos = LoginTemp.cesta.getPedidos();
                    for (Pedidos pe : listaPedidos) {
                        if (pe.getProducto().getNombre().equals(nombre)) {
                            pedido = pe;
                            break;
                        }
                    }
                    txtUni.setText("1");
                    nuevoPrecioTotal = Float.parseFloat(txtUni.getText()) * Float.parseFloat(price.getText());
                    totalPrice.setText(String.valueOf(nuevoPrecioTotal));
                    pedido.setCantidad(Integer.parseInt(txtUni.getText()));
                    pedido.setPrecioTotal(pedido.getProducto().getPrecio());
                    txtImporteTotal.setText(String.valueOf(Float.parseFloat(txtUni.getText()) * Float.parseFloat(price.getText())));

                }
            } catch (NumberFormatException ex) {
                lblQuejas.setText("Solo se admiten numeros");
            }
        }
    }

    public void actualizarImporteTotal(float precio, int uni) {
        txtImporteTotal.setText(String.valueOf(Float.parseFloat(txtImporteTotal.getText()) + precio * uni));
    }

    public void limpiarCesta() {
        vbProductos.getChildren().clear();
        txtImporteTotal.setText("0");
        LoginTemp.cesta = new Compras();
    }

    public void borrar(VBox vbProductos, HBox fila, String nombre, Label precioTotal) {

        Pedidos pedi = null;
        for (Pedidos ped : LoginTemp.cesta.getPedidos()) {

            if (ped.getProducto().getNombre().equals(nombre)) {
                pedi = ped;

            }
        }
        LoginTemp.cesta.getPedidos().remove(pedi);
        txtImporteTotal.setText(String.valueOf(Float.parseFloat(txtImporteTotal.getText()) - Float.parseFloat(precioTotal.getText())));
        vbProductos.getChildren().remove(fila);
    }

    public void realizarCompra() {
        Session s = NewHibernateUtil.getSession();
        Cuentas cuenta = null;
        boolean moroso = false;
        if (LoginTemp.cesta != null) {
            if (cmbPago.getSelectionModel().getSelectedItem() != null) {
                if (rbTrans.isSelected()) {
                    LoginTemp.cesta.setFormaPago(true);

                    cuenta = (Cuentas) s.get(Cuentas.class, cmbPago.getSelectionModel().getSelectedItem().toString());
                    if (cuenta.getSaldo() < (Float.parseFloat(txtImporteTotal.getText()))) {
                        lblQuejas.setText("No tienes saldo suficiente en tu cuenta para realizar este pedido, procedemos a borrar tu cuenta...");
                        moroso = true;
                    } else {
                        cuenta.setSaldo(cuenta.getSaldo() - (Float.parseFloat(txtImporteTotal.getText())));
                        LoginTemp.cesta.setCuenta(cuenta);
                    }
                } else {
                    LoginTemp.cesta.setFormaPago(false);
                    List<Cuentas> listaCuentas = s.createCriteria(Cuentas.class).list();
                    for (Cuentas i : listaCuentas) {
                        if (i.getTarjeta().getNumeroTarjeta().equals(cmbPago.getSelectionModel().getSelectedItem().toString())) {
                            cuenta = i;
                            break;
                        }
                    }

                    cuenta.setSaldo(cuenta.getSaldo() - (Float.parseFloat(txtImporteTotal.getText())));
                    LoginTemp.cesta.setCuenta(cuenta);
                }
                if (!moroso) {
                    for (Pedidos i : LoginTemp.cesta.getPedidos()) {
                        i.actualizarStock();
                    }
                    LoginTemp.setClienteActual((Clientes) s.get(Clientes.class, LoginTemp.getClienteActual().getId()));
                    LoginTemp.cesta.setFechaSolicitud(new Date());
                    LoginTemp.cesta.setCliente(LoginTemp.getClienteActual());
                    LoginTemp.getClienteActual().getCompras().add(LoginTemp.cesta);

                    Compras compra = new Compras(LoginTemp.cesta.getCliente(), LoginTemp.cesta.getCuenta(), LoginTemp.cesta.isFormaPago());
                    compra.setPrecioTotal(Float.parseFloat(txtImporteTotal.getText()));
                    s.beginTransaction();
                    s.save(compra);
                    s.getTransaction().commit();

                    List<Compras> listaCompras = s.createCriteria(Compras.class).list();
                    compra = listaCompras.get(listaCompras.size() - 1);
                    for (Pedidos pe : LoginTemp.cesta.getPedidos()) {
                        compra.getPedidos().add(pe);
                        compra.setCompletado(true);
                        pe.setCompra(compra);

                        s.beginTransaction();
                        s.saveOrUpdate(compra);
                        s.getTransaction().commit();

                    }
                    limpiarCesta();
                }
                lblQuejas.setText("");
            } else {
                lblQuejas.setText("Debes elegir una cuenta antes de realizar una compra");
            }
        }
        s.close();
    }
}
