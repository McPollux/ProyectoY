/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logintemp;

import Objetos.Clientes;
import Objetos.Compras;
import Objetos.Cuentas;
import Objetos.Pedidos;
import Objetos.Productos;
import Recursos.Inserciones;
import Recursos.Windows;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

/**
 *
 * @author jordi
 */
public class ControladorPedidos implements Initializable {

    private final Windows win = new Windows();
    @FXML
    private Pane panel;
    @FXML
    private JFXTextField txtBuscar;
    @FXML
    private Menu miNombre;
    @FXML
    private VBox vbProductos;

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

    public static List<Compras> listar() {
        List<Compras> p = new ArrayList();
        if (LoginTemp.bbdd == 0){
        Session s = NewHibernateUtil.getSession();
         p = s.createCriteria(Compras.class).list();
        s.close();}
        else{
            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
            CriteriaQuery cq = new CriteriaQuery(Compras.class);
            Objects<Compras> prod = odb.getObjects(cq);
            p.addAll(prod);
            odb.close();
        }
        return p;
    }

    @FXML
    public void rellenar() {
        ImageView img = new ImageView("/fotos/ImagenFalsa.jpg");
        MisPedidos(vbProductos, "Manzanas", "saben bien sdj hgdsj hgsd ojhfgsdj fhgsdjhfg sdjh fgsdjh gfjsdh fjhsd hjsd ", new Date("2018/12/12"), 12, 12, 500, img);
    }

    @FXML
    public void buscar() {
        List<Productos> pr = new ArrayList();
        Clientes cli = null;
        if (LoginTemp.bbdd == 0) {
            Session s = NewHibernateUtil.getSession();
            pr = s.createCriteria(Productos.class).list();
            s.close();
        } else {
            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
            CriteriaQuery cq = new CriteriaQuery(Clientes.class, Where.equal("dni", LoginTemp.getClienteActual().getDni()));
            Objects<Clientes> c = odb.getObjects(cq);
            cli = c.getFirst();
            odb.close();
        }
        vbProductos.getChildren().clear();
        for (Compras cc : cli.getCompras()) {
                for (Pedidos p : cc.getPedidos()) {
                    if (p.getProducto().getNombre().toLowerCase().matches(".*" + txtBuscar.getText().toLowerCase() + ".*")) {
                    try {
                        BufferedImage img = ImageIO.read(new ByteArrayInputStream(p.getProducto().getImg()));
                        Image imgProd = SwingFXUtils.toFXImage(img, null);
                        ImageView imgDefinitive = new ImageView(imgProd);

                        MisPedidos(vbProductos, p.getProducto().getNombre(), p.getProducto().getDescripcion(), cc.getFechaSolicitud(), p.getCantidad(), p.getPrecioTotal(),
                                p.getProducto().getPrecio(), imgDefinitive);
                    } catch (IOException ex) {

                    }
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

    public static void MisPedidos(VBox principal, String nombre, String descripcion, Date fecha, int uds, float precioTotal, float precio, ImageView imgProd) {
        principal.setSpacing(10);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        imgProd.setFitHeight(99);
        imgProd.setFitWidth(168);
        imgProd.setStyle("margin: 10px;");

        HBox fila = new HBox();
        fila.setPrefSize(740, 178);
        fila.setMinSize(740, 178);
        VBox columna1 = new VBox();
        columna1.setPrefSize(123, 145);
        Label entrega = new Label("Solicitado el " + sdf.format(fecha));
        entrega.setMinSize(170, 33);
        entrega.setStyle("-fx-label-padding: 10px;");
        columna1.getChildren().add(entrega);
        columna1.getChildren().add(imgProd);

        VBox columna2 = new VBox();
        columna2.setPrefSize(380, 145);
        Label name = new Label(nombre);
        name.setPrefSize(380, 42);
        name.setAlignment(Pos.CENTER);
        JFXTextArea description = new JFXTextArea(descripcion);

        description.setFocusColor(Paint.valueOf("#fff"));
        description.setUnFocusColor(Paint.valueOf("#fff"));
        description.setPrefSize(380, 114);
        description.setStyle("-fx-label-padding: 10px;");
        columna2.getChildren().add(name);
        columna2.getChildren().add(description);
        description.setStyle("-fx-border-color: black;");

        VBox columna3 = new VBox();
        columna3.setPrefSize(380, 145);
        Label ud = new Label("Unidades: " + uds);
        ud.setPrefSize(286, 50);
        ud.setAlignment(Pos.TOP_LEFT);
        Label precioU = new Label("Precio por unidad: " + precio);
        precioU.setPrefSize(291, 50);
        Label total = new Label("Total: " + precioTotal + "€");
        total.setPrefSize(291, 50);
        columna3.getChildren().add(ud);
        columna3.getChildren().add(precioU);
        columna3.getChildren().add(total);
        fila.getChildren().add(columna1);
        fila.getChildren().add(columna2);
        fila.getChildren().add(columna3);
        fila.setStyle("-fx-border-color: grey;");
        principal.getChildren().add(fila);
        fila.setSpacing(15);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MoverVentanas(panel);
        miNombre.setText(LoginTemp.getClienteActual().getNombre());
        if (LoginTemp.bbdd == 0) {
            Session s = NewHibernateUtil.getSession();
            Clientes cli = (Clientes) s.get(Clientes.class, LoginTemp.getClienteActual().getId());

            for (Compras c : cli.getCompras()) {
                for (Pedidos p : c.getPedidos()) {
                    try {
                        BufferedImage img = ImageIO.read(new ByteArrayInputStream(p.getProducto().getImg()));
                        Image imgProd = SwingFXUtils.toFXImage(img, null);
                        ImageView imgDefinitive = new ImageView(imgProd);

                        MisPedidos(vbProductos, p.getProducto().getNombre(), p.getProducto().getDescripcion(), c.getFechaSolicitud(), p.getCantidad(), p.getPrecioTotal(),
                                p.getProducto().getPrecio(), imgDefinitive);
                    } catch (IOException ex) {

                    }
                }
            }
            s.close();
        } else {
            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
            CriteriaQuery cq = new CriteriaQuery(Clientes.class, Where.equal("dni", LoginTemp.getClienteActual().getDni()));
            Objects<Clientes> c = odb.getObjects(cq);
            Clientes cli = c.getFirst();
             for (Compras cc : cli.getCompras()) {
                for (Pedidos p : cc.getPedidos()) {
                    try {
                        BufferedImage img = ImageIO.read(new ByteArrayInputStream(p.getProducto().getImg()));
                        Image imgProd = SwingFXUtils.toFXImage(img, null);
                        ImageView imgDefinitive = new ImageView(imgProd);

                        MisPedidos(vbProductos, p.getProducto().getNombre(), p.getProducto().getDescripcion(), cc.getFechaSolicitud(), p.getCantidad(), p.getPrecioTotal(),
                                p.getProducto().getPrecio(), imgDefinitive);
                    } catch (IOException ex) {

                    }
                }
            }
             odb.close();
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
}
