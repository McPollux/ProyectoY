/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recursos;

import java.util.List;
import java.util.Set;

import Objetos.*;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.geometry.Insets;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author jordi
 */
public class Inserciones {

    public static void AnhadirProducto(VBox principal, String nombre, String descripcion, float precio, ImageView imgProd) {
        ImageView imgPlus = new ImageView("/fotos/btnPlus1.png");
        principal.setSpacing(10);
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
        Label description = new Label(descripcion);
        columna2.getChildren().add(description);

        description.setPrefSize(216, 100);
        description.setStyle("-fx-padding: 10px;"); // debo revisar porque no funciona
        description.setStyle("-fx-border-color: black;");

        Label price = new Label("precio: " + precio);
        price.setPrefSize(200, 10);
        price.setAlignment(Pos.CENTER);
        columna.getChildren().add(price);
        columna.getChildren().add(imgPlus);
        principal.getChildren().add(columna);

    }

    public static void MisPedidos(VBox principal, String nombre, String descripcion, Date fecha, int uds, float precioTotal, float precio, ImageView imgProd) {
        principal.setSpacing(10);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        imgProd.setFitHeight(99);
        imgProd.setFitWidth(168);
        imgProd.setStyle("margin: 10px;");
        

     

        HBox fila = new HBox();
        fila.setPrefSize(740, 178);

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
        description.setPrefSize(380, 124);
        description.setStyle("-fx-label-padding: 10px;");
        columna2.getChildren().add(name);
        columna2.getChildren().add(description);

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
        

    }

    public static void comboBox(JFXComboBox<String> cb, Set<Object> lista, char tipo) {
        if (tipo == 'c') {
            for (Object c : lista) {
                /*Cuentas cu = (Cuentas)c;*/
                //cb.getItems().add(cu.getNumero());
                cb.getItems().add(c.toString());
            }
        } else {
            for (Object t : lista) {
                /*Tarjetas tar = (Tarjetas)t;
        		cb.getItems().add(tar.getNumero());*/
            }
        }

    }
}
