/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recursos;

import Objetos.Clientes;
import java.io.IOException;
import java.util.Optional;

import com.jfoenix.controls.JFXButton.ButtonType;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author jordi
 */
public class Windows {

  

    public void cerrarVentana(Node b) throws IOException {
        Stage stage = (Stage) b.getScene().getWindow();
        stage.close();

    }

    public void esconder(Node b) throws IOException {
        Stage stage = (Stage) b.getScene().getWindow();
        stage.hide();

    }

    public void abrirVentana(String ruta) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(ruta));
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public byte dialogo(String mensaje) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Dialogo de confirmación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje + "\nSe perderán los datos no guardados");

        Optional<javafx.scene.control.ButtonType> result = alert.showAndWait();
        if (result.get() == javafx.scene.control.ButtonType.OK) {
            return -1;
        } else {
            return 0;
        }
    }

  
}
