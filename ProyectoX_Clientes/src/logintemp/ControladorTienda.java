/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logintemp;

import Objetos.Compras;
import Objetos.Pedidos;
import Objetos.Productos;
import Recursos.Inserciones;
import Recursos.Windows;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.tk.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.hibernate.Session;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

/**
 *
 * @author jordi
 */
public class ControladorTienda implements Initializable {

    private final Windows win = new Windows();
    private final Inserciones ins = new Inserciones();
    @FXML
    private Pane panel;
    @FXML
    private MenuBar menubarTienda;

    @FXML
    private JFXTextField txtBuscar;

    @FXML
    private VBox vbProductos;

    @FXML
    private JFXButton btnCesta;
    @FXML
    private AnchorPane apProductos;
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

        byte b = win.dialogo("¿Deseas cerrar sesión?");
        if (b == -1) {
            LoginTemp.st.show();
            win.cerrarVentana(txtBuscar);

        }
    }

    @FXML
    public void miBanco() throws IOException {
        win.abrirVentana("/logintemp/MiBanco.fxml");
        win.cerrarVentana(txtBuscar);
    }

    @FXML
    public void cuenta() throws IOException {
        win.abrirVentana("/logintemp/Perfil.fxml");
        win.cerrarVentana(txtBuscar);
    }

    @FXML
    public void pedidosRealizados() throws IOException {
        win.abrirVentana("/logintemp/pedidos.fxml");
        win.cerrarVentana(txtBuscar);
    }

    @FXML
    public void cesta() throws IOException {
        win.abrirVentana("/logintemp/cesta.fxml");
        win.cerrarVentana(txtBuscar);
    }

    @FXML
    public void tienda() throws IOException {
        win.abrirVentana("/logintemp/tienda.fxml");
        win.cerrarVentana(txtBuscar);
    }

    @FXML
    public void productoJaj() {
        /*vbProductos.setSpacing(30);
        ImageView img = new ImageView("/fotos/ImagenFalsa.jpg");
        ins.AnhadirProducto(vbProductos, "Manzanas", "pues est�n ricas bb me encantan tus bideos vegeta\n t kiero bb",
                1500, img);*/
        //vbProductos.getChildren().clear();

        for (Pedidos object : LoginTemp.cesta.getPedidos()) {
            System.out.println(object.getCantidad() + "  " + object.getProducto().getNombre());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MoverVentanas(panel);
        miNombre.setText(LoginTemp.getClienteActual().getNombre());
        List<Productos> p = listar();
        for (Productos productos : p) {

            try {
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(productos.getImg()));
                Image imgProd = SwingFXUtils.toFXImage(img, null);
                ImageView imgDefinitive = new ImageView(imgProd);
                AnhadirProducto(vbProductos, productos.getNombre(), productos.getDescripcion(), productos.getPrecio(), imgDefinitive);
                //problema con la descripción, pone puntos supensivos en lugar de espaciar
            } catch (IOException ex) {
                Logger.getLogger(ControladorTienda.class.getName()).log(Level.SEVERE, null, ex);
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

    public static List<Productos> listar() {
        List<Productos> p = new ArrayList();
        if(LoginTemp.bbdd == 0){
        Session s = NewHibernateUtil.getSession();
        p = s.createCriteria(Productos.class).list();
        s.close();}
        else{
            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
            CriteriaQuery cq = new CriteriaQuery(Productos.class);
            Objects<Productos> prod = odb.getObjects(cq);
            p.addAll(prod);
            odb.close();
        }
        return p;
    }

    @FXML
    public void buscar() {
        List<Productos> p = new ArrayList<>();
        if (LoginTemp.bbdd == 0) {
            Session s = NewHibernateUtil.getSession();

            p = s.createCriteria(Productos.class).list();
            s.close();
        } else {
            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
            CriteriaQuery cq = new CriteriaQuery(Productos.class);
            Objects<Productos> prod = odb.getObjects(cq);
            p.addAll(prod);
            odb.close();
        }
        vbProductos.getChildren().clear();
        for (Productos productos : p) {

            if (productos.getNombre().toLowerCase().matches(".*" + txtBuscar.getText().toLowerCase() + ".*")) {
                try {
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(productos.getImg()));
                    Image imgProd = SwingFXUtils.toFXImage(img, null);
                    ImageView imgDefinitive = new ImageView(imgProd);
                    AnhadirProducto(vbProductos, productos.getNombre(), productos.getDescripcion(), productos.getPrecio(), imgDefinitive);
                } catch (IOException ex) {

                }
            }

        }
    }
    @FXML
    public void on_enter(Event evt) {
        KeyEvent e = (KeyEvent) evt;
        if (e.getCode() == KeyCode.ENTER) {

            try {
                buscar();
            } catch (Exception ex) {

            }

        }
    }

    public void AnhadirProducto(VBox principal, String nombre, String descripcion, float precio, ImageView imgProd) {
        byte b = 0;
        for (Pedidos pe : LoginTemp.cesta.getPedidos()) {
            if (pe.getProducto().getNombre().equals(nombre)) {
                b = 1;
            }
        }
        ImageView imgPlus = new ImageView("/fotos/btnPlus1.png");
        principal.setSpacing(10);
        imgProd.setStyle("-fx-margin: 30px;");
        HBox columna = new HBox();
        columna.setStyle("-fx-border-color: black;");
        columna.setSpacing(10);
        columna.setAlignment(Pos.CENTER);
        columna.setPrefSize(740, 150);
        VBox columna2 = new VBox();
        columna2.setPrefSize(216, 150);
        columna.getChildren().add(imgProd);
        columna.getChildren().add(columna2);

        Label name = new Label(nombre);
        name.setPrefSize(216, 30);
        name.setAlignment(Pos.CENTER);
        columna2.getChildren().add(name);
        JFXTextArea description = new JFXTextArea(descripcion);
        description.setEditable(false);
        description.setFocusColor(Paint.valueOf("#fff"));
        description.setUnFocusColor(Paint.valueOf("#fff"));
        columna2.getChildren().add(description);

        description.setPrefSize(216, 100);
        description.setStyle("-fx-padding: 10px;"); // debo revisar porque no funciona
        description.setStyle("-fx-border-color: black;");

        Label price = new Label("Precio: " + precio + "€");
        price.setPrefSize(200, 10);
        if (b == 0) {
            imgPlus.setOnMouseClicked((event) -> this.anhadirCesta(nombre, columna, imgPlus));
            imgPlus.setOnMousePressed((event) -> this.cabiarImgSum(imgPlus));
            imgPlus.setOnMouseReleased((event) -> this.volverSum(imgPlus));
        } else {
            Image check = new Image("/fotos/comprobado.png");
            imgPlus.setImage(check);
        }
        price.setAlignment(Pos.CENTER);
        columna.getChildren().add(price);
        columna.getChildren().add(imgPlus);
        principal.getChildren().add(columna);

    }

    public void cabiarImgSum(ImageView imgReal) {
        Image img = new Image("/fotos/btnPlus2.png");
        imgReal.setImage(img);
    }

    public void volverSum(ImageView imgReal) {
        Image img = new Image("/fotos/comprobado.png");
        imgReal.setImage(img);
    }

    public void anhadirCesta(String nombre, HBox columna, ImageView imgPlus) {
        System.out.println("Llegó a añadir cesta");
        List<Productos> p = new ArrayList<>();
        byte b = 0;
        if (LoginTemp.bbdd == 0) {
            Session s = NewHibernateUtil.getSession();
            p = s.createCriteria(Productos.class).list();
            s.close();
        } else {
            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
            CriteriaQuery cq = new CriteriaQuery(Productos.class);
            Objects<Productos> prod = odb.getObjects(cq);
            p.addAll(prod);
            odb.close();
        }
        for (Productos productos : p) {

            if (productos.getNombre().equals(nombre)) {
                for (Pedidos pedido : LoginTemp.cesta.getPedidos()) {
                    if (pedido.getProducto().getNombre().equals(nombre)) {
                        b = 1;
                        //columna.getChildren().remove(imgPlus);
                        //ImageView imgCheck = new ImageView("/fotos/comprobado.png");
                        //columna.getChildren().add(imgCheck);
                    }
                }
                if (b == 0) {
                    Pedidos ped = new Pedidos(1, productos.getPrecio(), productos, LoginTemp.cesta);
                    LoginTemp.cesta.getPedidos().add(ped);
                    //columna.getChildren().remove(imgPlus);
                    //ImageView imgCheck = new ImageView("/fotos/comprobado.png");
                    //columna.getChildren().add(imgCheck);
                }

            }

        }

    }

}
