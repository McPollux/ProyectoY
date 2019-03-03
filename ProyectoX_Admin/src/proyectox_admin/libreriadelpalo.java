/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectox_admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author mi
 */
public class libreriadelpalo   {

    
         public static byte dialogo(String a) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dialogo de confirmación");
        alert.setHeaderText(null);
        alert.setContentText(a);
         Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            return -1;
        }

        return 0;
    }
         
                  public static void dialogoinformacion(String a) {
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText(a);
         Optional<ButtonType> result = alert.showAndWait();
      }
    
public  void cerrarconboton(Event evt){

    try{
    ((Stage) ((JFXButton)evt.getSource()).getScene().getWindow()).close();
    }catch(ClassCastException e){
            System.out.println(evt.getSource());
    try{
    ((Stage) ((JFXTextField)evt.getSource()).getScene().getWindow()).close();
    }catch(ClassCastException es){               
        ((Stage) ((JFXPasswordField)evt.getSource()).getScene().getWindow()).close();}}
    
    }

public  void abrirconpanel(String s) throws IOException{
     Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(s));             
        Scene scene = new Scene(root);        
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();         

   
}
public void visualizaralta(VBox AltasProductos, String nombre,String combo,String precio,String descripcion,String stackactual,String stcokmin,String stockmax,File imagen){
    if(!precio.isEmpty()){ 
    precio+="€";}else{precio="";}
     ((Label)((HBox)AltasProductos.getChildren().get(0)).getChildren().get(0)).setText(combo);
     ((Label)((HBox)((VBox)((HBox)AltasProductos.getChildren().get(0)).getChildren().get(1)).getChildren().get(1)).getChildren().get(1)).setText(stackactual);
     ((Label)((HBox)((VBox)((HBox)AltasProductos.getChildren().get(0)).getChildren().get(1)).getChildren().get(1)).getChildren().get(3)).setText(stcokmin);
     ((Label)((HBox)((VBox)((HBox)AltasProductos.getChildren().get(0)).getChildren().get(1)).getChildren().get(1)).getChildren().get(5)).setText(stockmax);
     ((Label)((HBox)AltasProductos.getChildren().get(1)).getChildren().get(0)).setText(nombre);
     ((JFXTextArea)((HBox)AltasProductos.getChildren().get(1)).getChildren().get(1)).setText(descripcion);
     ((Label)((HBox)AltasProductos.getChildren().get(1)).getChildren().get(2)).setText(precio);
     try{
     ((ImageView)((HBox)AltasProductos.getChildren().get(1)).getChildren().get(3)).setImage(new Image(imagen.toURI().toString()));
     }catch(Exception e){}

}
public void vaciarvisualizaralta(VBox AltasProductos){
     
     ((Label)((HBox)AltasProductos.getChildren().get(0)).getChildren().get(0)).setText("");
     ((Label)((HBox)((VBox)((HBox)AltasProductos.getChildren().get(0)).getChildren().get(1)).getChildren().get(1)).getChildren().get(1)).setText("");
     ((Label)((HBox)((VBox)((HBox)AltasProductos.getChildren().get(0)).getChildren().get(1)).getChildren().get(1)).getChildren().get(3)).setText("");
     ((Label)((HBox)((VBox)((HBox)AltasProductos.getChildren().get(0)).getChildren().get(1)).getChildren().get(1)).getChildren().get(5)).setText("");
     ((Label)((HBox)AltasProductos.getChildren().get(1)).getChildren().get(0)).setText("");
     ((JFXTextArea)((HBox)AltasProductos.getChildren().get(1)).getChildren().get(1)).setText("");
     ((Label)((HBox)AltasProductos.getChildren().get(1)).getChildren().get(2)).setText("");
     try{
     ((ImageView)((HBox)AltasProductos.getChildren().get(1)).getChildren().get(3)).setImage(null);
     }catch(Exception e){}

}
    
    
}
