/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectox_admin;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import funciones.Altas;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**         vaciar campos    
 *
 * @author mi
 */
public class RegistraryLogear implements Initializable {
     @FXML
    private final libreriadelpalo lib=new libreriadelpalo();      
     



     
    @FXML
    private JFXRadioButton neobuton,hiberbuton;
//REGISTRACION
    @FXML
    private JFXTextField regusuario;
    @FXML
    private JFXPasswordField regcontraseña;
    @FXML
    private JFXPasswordField regcontraseña2;   
       
    @FXML
    private Label Eregusuario,Eregcontraseña,Eregcontraseña2;

//REGISTRACION                        
   //LOGEACION         
     @FXML
    private JFXTextField logusuario;
    @FXML
    private JFXPasswordField logcontraseña;   
        @FXML
    private Label Elogusuario,Elogcontraseña;
    //LOGEACION         
        String loli="UPUPDOWNDOWNLEFTRIGHTLEFTRIGHTBA";
        String lala="";
            
    @FXML
    private ImageView elproimg;
    
    
        @FXML
    void neoclicked(MouseEvent event) {
    System.out.println("neodatis selected");
    ProyectoX_Admin.gestor=1;
    }
    
             @FXML
    void hiberclicked(MouseEvent event) {
         System.out.println("hibernate selected");
             ProyectoX_Admin.gestor=0;
    }
    
                  @FXML
    private void on_salir(Event evt) {
    System.exit(-1);
    }
        @FXML
    private void on_atras(Event evt) throws IOException {
        lib.abrirconpanel("Log_in_Administradores.fxml") ;
        lib.cerrarconboton(evt);        
        
}  
      File a = new File("src\\proyectoX\\imagenes\\mesas.jpg");
      
                
    @FXML
    private void onRegistrarse(Event evt) throws IOException {
        lib.abrirconpanel("Registrar_Administradores.fxml") ;
        lib.cerrarconboton(evt);        
      
}  
    @FXML
    private void on_enter(Event evt){
  
        KeyEvent e=(KeyEvent)evt;
     
        lala+=String.valueOf(e.getCode());
         System.out.println(lala);
         System.out.println(loli);
            if(lala.equalsIgnoreCase(loli)){System.out.println("Debeirai ir");elproimg.setImage(new Image(a.toURI().toString()));}
       if(e.getCode()==KeyCode.ENTER){
           
        
           lala="";
           String id=((Node)evt.getSource()).getScene().getRoot().getId();
           System.out.println(id);
                    try {
       switch(id){
           case "Login":onLogear(evt);break;
           case "Register":onRegistrocompletado(evt);break;}
       
           } catch (IOException ex) {            
           }       
       }        
    }
    
    @FXML
    private void onRegistrocompletado(Event evt) throws IOException {
           System.out.println(regusuario.getText()+"   "+regcontraseña.getText()+"  "+regcontraseña2.getText());
        if(Altas.administradores(regusuario.getText(),regcontraseña.getText(),regcontraseña2.getText(),Eregusuario,Eregcontraseña,Eregcontraseña2)){
        lib.abrirconpanel("Log_in_Administradores.fxml") ;     
        lib.cerrarconboton(evt);
        }}
        @FXML
        private void onLogear(Event evt) throws IOException { 
           if(Altas.login(logusuario.getText(),logcontraseña.getText(),Elogusuario,Elogcontraseña)){
          
            lib.abrirconpanel("Administrador.fxml") ;     
            lib.cerrarconboton(evt);     
           } 
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
