/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logintemp;

import Funciones.Validaciones;
import Objetos.Clientes;
import Recursos.Windows;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.OID;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

/**
 *
 * @author jordi
 */
public class ControladorCuenta implements Initializable {

    private final Windows win = new Windows();
    @FXML
    private Pane pane;
    @FXML
    private JFXTextField txtCorreo;
    @FXML
    private Menu miNombre;
    @FXML
    private JFXTextField txtUsuario;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXTextField txtDireccion;

    @FXML
    private JFXTextField txtTelf;
    @FXML
    private JFXButton btnAceptar;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    private Label lblAdvertencia;

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
            win.cerrarVentana(txtCorreo);

        }
    }

    @FXML
    public void miBanco() throws IOException {
        win.abrirVentana("/logintemp/MiBanco.fxml");
        win.cerrarVentana(txtCorreo);
    }

    @FXML
    public void cuenta() throws IOException {
        win.abrirVentana("/logintemp/Perfil.fxml");
        win.cerrarVentana(txtCorreo);
    }

    @FXML
    public void pedidosRealizados() throws IOException {
        win.abrirVentana("/logintemp/pedidos.fxml");
        win.cerrarVentana(txtCorreo);
    }

    @FXML
    public void cesta() throws IOException {
        win.abrirVentana("/logintemp/cesta.fxml");
        win.cerrarVentana(txtCorreo);
    }

    @FXML
    public void tienda() throws IOException {
        win.abrirVentana("/logintemp/tienda.fxml");
        win.cerrarVentana(txtCorreo);
    }

    @FXML
    public void ajustes() {
        txtCorreo.setEditable(true);
        txtNombre.setEditable(true);
        txtTelf.setEditable(true);
        txtDireccion.setEditable(true);
        btnCancelar.setVisible(true);
        btnAceptar.setVisible(true);
        colorinesEditar();
    }

    @FXML
    public void aceptar() {
        lblAdvertencia.setAlignment(Pos.CENTER);
        String mensaje;
        boolean b = true;

        if (!Validaciones.mail(txtCorreo.getText())) {
            b = false;
            //imgMail.setVisible(true);
            lblAdvertencia.setText("Formato de correo inválido");
        } else {
            //imgMail.setVisible(false);
        }

        mensaje = Validaciones.telf(txtTelf.getText());
        if (!mensaje.equals("")) {
            b = false;
            //imgTlf.setVisible(true);
            lblAdvertencia.setText(mensaje);
        } else {
            //imgTlf.setVisible(false);
        }
        if (b) {
            LoginTemp.getClienteActual().setCorreo(txtCorreo.getText());
            LoginTemp.getClienteActual().setTelefono(txtTelf.getText());
            LoginTemp.getClienteActual().setDireccion(txtDireccion.getText());
            LoginTemp.getClienteActual().setNombre(txtNombre.getText());
            if (LoginTemp.bbdd == 0) {
                Session s = NewHibernateUtil.getSession();
                s.beginTransaction();
                s.saveOrUpdate(LoginTemp.getClienteActual());
                s.getTransaction().commit();
                s.close();
            } else {

                ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
                CriteriaQuery cq = new CriteriaQuery(Clientes.class, Where.equal("user", LoginTemp.getClienteActual().getUser()));
                Clientes cli = (Clientes) (odb.getObjects(cq)).getFirst();
                cli.setCorreo(txtCorreo.getText());
                cli.setTelefono(txtTelf.getText());
                cli.setDireccion(txtDireccion.getText());
                cli.setNombre(txtNombre.getText());
                odb.store(cli);
                odb.commit();
                odb.close();
            }
            lblAdvertencia.setText("");
            noEdit();
        }

    }

    public void noEdit() {

        txtCorreo.setEditable(false);
        txtNombre.setEditable(false);
        txtTelf.setEditable(false);
        txtDireccion.setEditable(false);
        btnCancelar.setVisible(false);
        btnAceptar.setVisible(false);
        colorines();

    }

    public void colorines() {
        txtCorreo.setUnFocusColor(Paint.valueOf("#9DD7BF"));
        txtCorreo.setFocusColor(Paint.valueOf("#9DD7BF"));

        txtNombre.setUnFocusColor(Paint.valueOf("#9DD7BF"));
        txtNombre.setFocusColor(Paint.valueOf("#9DD7BF"));

        txtTelf.setUnFocusColor(Paint.valueOf("#9DD7BF"));
        txtTelf.setFocusColor(Paint.valueOf("#9DD7BF"));

        txtDireccion.setUnFocusColor(Paint.valueOf("#9DD7BF"));
        txtDireccion.setFocusColor(Paint.valueOf("#9DD7BF"));

        txtUsuario.setUnFocusColor(Paint.valueOf("#9DD7BF"));
        txtUsuario.setFocusColor(Paint.valueOf("#9DD7BF"));
    }

    public void colorinesEditar() {
        txtCorreo.setUnFocusColor(Paint.valueOf("#000"));
        txtCorreo.setFocusColor(Paint.valueOf("#000"));

        txtNombre.setUnFocusColor(Paint.valueOf("#000"));
        txtNombre.setFocusColor(Paint.valueOf("#000"));

        txtTelf.setUnFocusColor(Paint.valueOf("#000"));
        txtTelf.setFocusColor(Paint.valueOf("#000"));

        txtDireccion.setUnFocusColor(Paint.valueOf("#000"));
        txtDireccion.setFocusColor(Paint.valueOf("#000"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtCorreo.setText(LoginTemp.getClienteActual().getCorreo());
        txtUsuario.setText(LoginTemp.getClienteActual().getUser());
        txtNombre.setText(LoginTemp.getClienteActual().getNombre());
        txtDireccion.setText(LoginTemp.getClienteActual().getDireccion());
        txtTelf.setText(LoginTemp.getClienteActual().getTelefono());
        miNombre.setText(LoginTemp.getClienteActual().getNombre());
        colorines();
        MoverVentanas(pane);
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
