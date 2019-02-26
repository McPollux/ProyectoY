/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logintemp;

import Objetos.Clientes;
import Recursos.Windows;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;

/**
 *
 * @author jordi
 */
public class FXMLDocumentController implements Initializable {

    private Windows win = new Windows();
    @FXML
    private ImageView btnlogin;
    
    @FXML
    private Pane panel;
    @FXML
    private JFXTextField txtUser;

    @FXML
    private JFXPasswordField txtPass;

    @FXML
    private Label txtAviso;

    @FXML
    public void registro(ActionEvent event) throws IOException {
        win.abrirVentana("/logintemp/Registrar.fxml");
        win.cerrarVentana(txtUser);
    }

    @FXML
    public void login() throws IOException {

        if (confirmarCliente()) {
            win.abrirVentana("/logintemp/tienda.fxml");
            win.esconder(txtUser);
        } else {
            txtAviso.setText("Nombre de usuario o contraseña incorrectos");
        }
    }

    public boolean confirmarCliente() {
        Session s = NewHibernateUtil.getSession();
        List<Clientes> clientes = s.createCriteria(Clientes.class).list();
        boolean b = false;
        for (Clientes c : clientes) {
            if (c.getUser().equals(txtUser.getText())) {
                if (c.getContrasenha() == txtPass.getText().hashCode()) {
                    b = true;
                    LoginTemp.setClienteActual(c);  
                }
                break;
            }
        }
        s.close();
        return b;
    }

    @FXML
    public void on_enter(Event evt) {
        KeyEvent e = (KeyEvent) evt;
        if (e.getCode() == KeyCode.ENTER) {

            try {
                login();
            } catch (IOException ex) {
                
            }

        }
    }

    @FXML
    private void salir() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       txtAviso.setAlignment(Pos.CENTER);
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
