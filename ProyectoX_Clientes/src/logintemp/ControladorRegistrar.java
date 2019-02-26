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
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

/**
 *
 * @author jordi
 */
public class ControladorRegistrar implements Initializable {

    private final Windows win = new Windows();
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private Pane panel;
    @FXML
    private Label lblAviso;

    @FXML
    private JFXTextField txtUsuario;

    @FXML
    private TextField txtNombre;

    @FXML
    private JFXTextField txtTelefono;

    @FXML
    private JFXTextField txtDireccion;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtDni;

    @FXML
    private JFXPasswordField txtPass;

    @FXML
    private JFXPasswordField txtRePass;

    @FXML
    private void salirRegistro() throws IOException {

        win.abrirVentana("/logintemp/FXMLDocument.fxml");
        win.cerrarVentana(txtDni);

    }

    @FXML
    public void registro() throws IOException {
        lblAviso.setText("");
        String mensaje = Validaciones.dni(txtDni.getText());
        if (!mensaje.equals("")) {
            lblAviso.setText(mensaje);
        } else {
            mensaje = Validaciones.usuario(txtUsuario.getText());
            if (!mensaje.equals("")) {
                lblAviso.setText(mensaje);
            } else {
                mensaje = Validaciones.Contrasenha(txtPass, txtRePass);
                if (!mensaje.equals("")) {
                    lblAviso.setText(mensaje);
                } else {
                    mensaje = Validaciones.telf(txtTelefono.getText());
                    if (!mensaje.equals("")) {
                        lblAviso.setText(mensaje);
                    } else {
                        boolean b = Validaciones.mail(txtEmail.getText());
                        if (b) {
                            if (LoginTemp.bbdd == 0) {
                                Session s = NewHibernateUtil.getSession();
                                int contra = txtPass.getText().hashCode();
                                Clientes c = new Clientes(txtUsuario.getText(),
                                        contra, txtDni.getText(), txtNombre.getText(), txtDireccion.getText(),
                                        txtTelefono.getText(), txtEmail.getText());
                                s.beginTransaction();
                                s.saveOrUpdate(c);
                                s.getTransaction().commit();
                                s.close();
                            } else {
                                ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
                                int contra = txtPass.getText().hashCode();
                                Clientes c = new Clientes(txtUsuario.getText(),
                                        contra, txtDni.getText(), txtNombre.getText(), txtDireccion.getText(),
                                        txtTelefono.getText(), txtEmail.getText());
                                odb.store(c);
                                odb.close();
                            }
                             win.abrirVentana("/logintemp/FXMLDocument.fxml");
                                win.cerrarVentana(txtDni);
                        } else {

                            lblAviso.setText("introduce un formato válido para el correo");
                        }
                    }
                }

            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MoverVentanas(panel);
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
