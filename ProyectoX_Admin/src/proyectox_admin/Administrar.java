/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectox_admin;

import Objetos.Clientes;
import Objetos.Gestores;
import Objetos.Productos;
import Objetos.Proveedores;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import funciones.Altas;
import funciones.Bajas;
import funciones.Modificar;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import org.hibernate.Session;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import recursos.Busquedas;
import recursos.Resize;

/**
 *
 * @author mi
 */
public class Administrar implements Initializable {

    @FXML
    private final libreriadelpalo lib = new libreriadelpalo();

    File imagen = null;

    // ***************************************************************************************************************************************
    //   ALTAS PRODUCTOS
    @FXML
    private JFXTextField Altanombre, Altaprecio, AltaStockActual, AltaStockMin, AltaStockMax;

    @FXML
    private JFXTextArea Altadescripcion;
    @FXML
    private JFXComboBox<String> altaProduProv;
    @FXML
    private VBox AltasProductos;
    //   ALTAS PRODUCTOS

//   MODIFICAR PRODUCTOS
    @FXML
    private JFXTextField modNombre, modStockMax, modStockAct, modPrecio, modStockMin;
    @FXML
    private JFXTextArea moddescrip;

    @FXML
    private VBox vboxModificarltaProducto;

    //   MODIFICAR PRODUCTOS
    // *************************************************************************************************************************************** 
    //   ALTAS PROOVEDOR
    @FXML
    private JFXTextField cifAlta, direccionalta, telefonoAlta, emailAlta;
    @FXML
    private Label Ealprocif, Ealprodir, Ealprotlf, Ealpromail;
    //   ALTAS PROOVEDOR    

    //   MODIFICACIONES PROOVEDOR
    @FXML
    private JFXTextField modProvCif, modProvTlf, modProvMail, modProvDir;

    @FXML
    private VBox vboxModificacionesProveedores;
    //   MODIFICACIONES PROOVEDOR    

    //   BAJAS PROOVEDOR
    @FXML
    private VBox vboxBajasProveedores;
    //   BAJAS PROOVEDOR

    // ***************************************************************************************************************************************
    //   MODIFICACIONES CLIENTE
    @FXML
    private JFXTextField modCliNombre, modCliDirec, modCliMail, modCliTlf;

    @FXML
    private VBox vboxModificacionesClientes;
    //   MODIFICACIONES CLIENTE

    //   BAJAS CLIENTE
    @FXML
    private VBox vboxBajasClientes;
    //   BAJAS CLIENTE

    // ***************************************************************************************************************************************
    // ***************************************************************************************************************************************
    //ALTAS  //ALTAS  //ALTAS  //ALTAS  //ALTAS  //ALTAS  //ALTAS  //ALTAS  //ALTAS  //ALTAS 
    @FXML
    private void onDarDeAltaProducto(Event evt) {
        boolean valido;
        try {
            System.out.println(altaProduProv.getSelectionModel().getSelectedItem());
            valido = Altas.productos(Altanombre.getText(), altaProduProv.getSelectionModel().getSelectedItem(), Altaprecio.getText(), AltaStockMin.getText(), AltaStockActual.getText(), AltaStockMax.getText(), Altadescripcion.getText(), imagen);

            if (valido) {
                Altanombre.setText("");
                Altaprecio.setText("");
                AltaStockMin.setText("");
                Altanombre.setText("");
                AltaStockActual.setText("");
                AltaStockMax.setText("");
                Altadescripcion.setText("");
                imagen = null;
                lib.vaciarvisualizaralta(AltasProductos);
                altaProduProv.getSelectionModel().select(-1);
            }
        } catch (IOException ex) {
        }
    }

    @FXML
    private void onAltaProovedor(Event evt) {

        if (Altas.proovedor(cifAlta.getText(), direccionalta.getText(), telefonoAlta.getText(), emailAlta.getText(), Ealprocif, Ealprodir, Ealprotlf, Ealpromail)) {
            cifAlta.setText("");
            direccionalta.setText("");
            telefonoAlta.setText("");
            emailAlta.setText("");
            combo_productos();
        }
    }
    //ALTAS  //ALTAS  //ALTAS  //ALTAS  //ALTAS  //ALTAS  //ALTAS  //ALTAS  //ALTAS 
//MODIFICACIONES//MODIFICACIONES//MODIFICACIONES//MODIFICACIONES

    @FXML
    private void on_modificar_productos(Event evt) {

        try {
            System.out.println("d2");
            Modificar.productos(auxprod, modNombre.getText(), moddescrip.getText(), modPrecio.getText(), modStockMin.getText(), modStockAct.getText(), modStockMax.getText(), auximg);
            onTabModificacionesProducto(evt);
        } catch (IOException ex) {

        }

    }

    @FXML
    private void onModificarProovedor(Event evt) {
        System.out.println("onmodpro");
        Modificar.proveedores(auxprov, modProvDir.getText(), modProvMail.getText(), modProvTlf.getText());
        System.out.println("onModificarProovedor");

        on_provmod(evt);

    }

    @FXML
    private void onModificarCliente(Event evt) {
        try {
            Modificar.clientes(auxclien, modCliNombre.getText(), modCliDirec.getText(), modCliTlf.getText(), modCliMail.getText());
            onTab_BajasClientes();
        } catch (IOException ex) {

        }
        System.out.println("onModificarCliente");

    }
//MODIFICACIONES//MODIFICACIONES//MODIFICACIONES//MODIFICACIONES
//BAJAS //BAJAS //BAJAS //BAJAS //BAJAS //BAJAS 

    @FXML
    private void BajasProveedores(Event evt, Proveedores p) {

        System.out.println("onBajasProveedores");
        byte b = lib.dialogo("SEguro que quieres dar de baja este proovedor");
        if (b == -1) {
            System.out.println("baba");
            Bajas.proveedores(p);
            ondeselegirprovedor(evt);
            on_probajas(evt);
        } else {
            ondeselegirprovedor(evt);
        }
    }

    @FXML
    private void onBajasCliente(Event evt, Clientes c) {

        System.out.println("onBajasCliente");
        byte b = lib.dialogo("SEguro que quieres dar de baja este Cliente");
        if (b == -1) {
            System.out.println("baba");
            Bajas.clientes(c);
            onTab_BajasClientes();
        }
        ondeselegirprovedor(evt);

    }
//BAJAS //BAJAS //BAJAS //BAJAS //BAJAS //BAJAS //BAJAS 
    // ***************************************************************************************************************************************
    // **************************************** DE AQUI PARA ABAJO ESTO NO TE INTERESA**************************************************
    Proveedores auxprov = null;
    Productos auxprod = null;
    Clientes auxclien = null;
    byte[] auximg = null;

    public void combo_productos() {
        List<Proveedores> lista = Busquedas.listaProveedores();
        altaProduProv.getItems().clear();
        for (Proveedores p : lista) {
            altaProduProv.getItems().add(p.getCif());
        }
    }

    @FXML
    private void onvisAltaProducto(Event evt) {
        lib.visualizaralta(AltasProductos, Altanombre.getText(), altaProduProv.getSelectionModel().getSelectedItem(), Altaprecio.getText(), Altadescripcion.getText(), AltaStockActual.getText(), AltaStockMin.getText(), AltaStockMax.getText(), imagen);

    }

    @FXML
    private void deselegirproducto(Event evt) throws IOException {

        HBox h = (HBox) evt.getSource();
        h.setStyle("-fx-background-color: white; ");
        System.out.println(((Label) h.getChildren().get(0)).getText());

    }

    @FXML
    private void onelegirproducto(Event evt) throws IOException {
        HBox h = (HBox) evt.getSource();
        String a = (((Label) h.getChildren().get(0)).getText());


            if(ProyectoX_Admin.gestor==0){
              Session s = NewHibernateUtil.getSession();
            Productos p = (Productos) s.get(Productos.class, Integer.valueOf(a));
            auxprod = p;
            s.close();
            }else{
            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
            Objects<Productos> prod = odb.getObjects(new CriteriaQuery(Productos.class, Where.equal("id", Integer.valueOf(a))));
            Productos p = prod.getFirst();
            auxprod = p;               
                
            }
        
        
        
        modNombre.setText(auxprod.getNombre());
        System.out.println(auxprod.getDescripcion());
        moddescrip.setText(auxprod.getDescripcion());
        modStockMin.setText(String.valueOf(auxprod.getStockMin()));
        modStockAct.setText(String.valueOf(auxprod.getStockActual()));
        modStockMax.setText(String.valueOf(auxprod.getStockMax()));
        modPrecio.setText(String.valueOf(auxprod.getPrecio()));
        auximg = auxprod.getImg();

        h.setStyle("-fx-background-color: slateblue; ");
     
    }

    @FXML
    private void onelegircliente(Event evt, byte op) throws IOException {
        HBox h = (HBox) evt.getSource();
        String a = (((Label) h.getChildren().get(0)).getText());
        Session s = NewHibernateUtil.getSession();
        Clientes c = (Clientes) s.get(Clientes.class, Integer.valueOf(a));
        auxclien = c;
        h.setStyle("-fx-background-color: slateblue; ");
        s.close();
        if (op == 1) {
            modCliNombre.setText(c.getNombre());
            modCliDirec.setText(c.getDireccion());
            modCliTlf.setText(c.getTelefono());
            modCliMail.setText(c.getCorreo());

        } else {

            onBajasCliente(evt, c);
        }
    }

    @FXML
    private void on_salir(Event evt) throws IOException {
        System.exit(-1);
    }

    @FXML
    private void onElegirImagen(Event evt) throws IOException {

        FileChooser fc = new FileChooser();
        fc.setTitle("Open Resource File");
        File a = new File("src\\proyectoX\\imagenes");
        fc.setInitialDirectory(a);
        File selectedFile = fc.showOpenDialog(null);

        imagen = selectedFile.getAbsoluteFile();
        if (((Node) evt.getSource()).getParent().getId().equalsIgnoreCase("paneprodmod")) {
            BufferedImage bufferimage = ImageIO.read(new File(imagen.toURI()));
            BufferedImage resized = Resize.resize(bufferimage);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(resized, "jpg", output);
            auximg = output.toByteArray();

        }
        onvisAltaProducto(evt);
    }

    @FXML

    private void onTabModificacionesProducto(Event evt) throws IOException {
        vboxModificarltaProducto.getChildren().clear();

        List<Productos> lista = Busquedas.listaProductos();
        for (Productos i : lista) {
            HBox h = new HBox();

            h.setOnMousePressed((event) -> {
                try {
                    onelegirproducto(event);
                } catch (IOException ex) {
                }
            });

            h.setOnMouseReleased((event) -> {
                try {
                    deselegirproducto(event);
                } catch (IOException ex) {
                }
            });
            /*   BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(i.getImg()));
            Image imgProd = SwingFXUtils.toFXImage(bufferedImage, null);
            ImageView a=new ImageView(imgProd);*/
            h.setPrefSize(662, 10);
            Label id = new Label(String.valueOf(i.getId()));
            Label nombre = new Label(i.getNombre());
            Label stock = new Label(String.valueOf(i.getStockActual()));
            id.setPrefSize(220, 17);
            nombre.setPrefSize(220, 17);
            stock.setPrefSize(225, 17);
            id.setAlignment(Pos.CENTER);
            stock.setAlignment(Pos.CENTER);
            nombre.setAlignment(Pos.CENTER);

            h.getChildren().addAll(id, nombre, stock);
            try {

                vboxModificarltaProducto.getChildren().add(h);
            } catch (NullPointerException e) {
            }
        }
    }

    @FXML
    private void onTab_BajasClientes() {
        vboxclientes(0);
        vboxclientes(1);
    }

    private void vboxclientes(int a) {
        byte op;
        VBox aux;

        if (a == 0) {
            op = 0;
            aux = vboxBajasClientes;
        } else {
            op = 1;
            aux = vboxModificacionesClientes;
        }
        try {
            aux.getChildren().clear();
        } catch (NullPointerException e) {
        }
        List<Clientes> lista = Busquedas.listaClientes();

        for (Clientes i : lista) {
            HBox h = new HBox();
            h.setOnMousePressed((event) -> {
                try {
                    onelegircliente(event, op);
                } catch (IOException ex) {
                }
            });

            h.setOnMouseReleased((event) -> {
                ondeselegirprovedor(event);
            });
            h.setPrefSize(662, 10);
            Label id = new Label(String.valueOf(i.getId()));
            Label dni = new Label(i.getDni());
            Label nombre = new Label(i.getNombre());
            Label direc = new Label(i.getDireccion());
            Label tlf = new Label(i.getTelefono());
            id.setPrefSize(50, 19);
            dni.setPrefSize(98, 19);
            nombre.setPrefSize(144, 19);
            direc.setPrefSize(257, 19);
            tlf.setPrefSize(112, 19);
            id.setAlignment(Pos.CENTER);
            dni.setAlignment(Pos.CENTER);
            nombre.setAlignment(Pos.CENTER);
            direc.setAlignment(Pos.CENTER);
            tlf.setAlignment(Pos.CENTER);
            h.getChildren().addAll(id, dni, nombre, direc, tlf);
            try {
                aux.getChildren().add(h);
            } catch (NullPointerException e) {
            }
        }
    }

    @FXML
    private void on_provmod(Event evt) {
        vboxModificacionesProveedores.getChildren().clear();

        List<Proveedores> lista = Busquedas.listaProveedores();

        for (Proveedores i : lista) {
            HBox h = new HBox();
            h.setOnMousePressed((event) -> {
                try {
                    onelegirprovedor(event, 1);
                } catch (IOException ex) {
                }
            });

            h.setOnMouseReleased((event) -> {
                ondeselegirprovedor(event);
            });
            h.setPrefSize(662, 10);
            Label cif = new Label(i.getCif());
            Label direc = new Label(i.getDireccion());
            Label tlf = new Label(i.getTelefono());
            Label correo = new Label(i.getCorreo());
            cif.setPrefSize(58, 19);
            direc.setPrefSize(265, 19);
            tlf.setPrefSize(97, 19);
            correo.setPrefSize(223, 19);
            cif.setAlignment(Pos.CENTER);
            tlf.setAlignment(Pos.CENTER);
            direc.setAlignment(Pos.CENTER);
            correo.setAlignment(Pos.CENTER);
            h.getChildren().addAll(cif, direc, tlf, correo);
            try {
                vboxModificacionesProveedores.getChildren().add(h);
            } catch (NullPointerException e) {
            }

        }

    }

    @FXML
    private void on_probajas(Event evt) {
        vboxBajasProveedores.getChildren().clear();

        List<Proveedores> lista = Busquedas.listaProveedores();

        for (Proveedores i : lista) {
            HBox h = new HBox();
            h.setOnMousePressed((event) -> {
                try {
                    onelegirprovedor(event, 0);
                } catch (IOException ex) {
                }
            });

            h.setOnMouseReleased((event) -> {
                ondeselegirprovedor(event);
            });
            h.setPrefSize(662, 10);
            Label cif = new Label(i.getCif());
            Label direc = new Label(i.getDireccion());
            Label correo = new Label(i.getCorreo());
            Label tlf = new Label(i.getTelefono());
            cif.setPrefSize(58, 19);
            direc.setPrefSize(265, 19);
            tlf.setPrefSize(97, 19);
            correo.setPrefSize(223, 19);
            cif.setAlignment(Pos.CENTER);
            tlf.setAlignment(Pos.CENTER);
            direc.setAlignment(Pos.CENTER);
            correo.setAlignment(Pos.CENTER);
            h.getChildren().addAll(cif, direc, tlf, correo);
            try {
                vboxBajasProveedores.getChildren().add(h);
            } catch (NullPointerException e) {
            }
        }

    }

    @FXML
    private void ondeselegirprovedor(Event evt) {
        HBox h = (HBox) evt.getSource();
        h.setStyle("-fx-background-color: white; ");

    }

    @FXML
    private void onelegirprovedor(Event evt, int op) throws IOException {
        HBox h = (HBox) evt.getSource();
        String a = (((Label) h.getChildren().get(0)).getText());      
                    if(ProyectoX_Admin.gestor==0){
              Session s = NewHibernateUtil.getSession();
            Proveedores p = (Proveedores) s.get(Proveedores.class, a);
            auxprov = p;
            s.close();
            }else{
            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
            Objects<Proveedores> prov = odb.getObjects(new CriteriaQuery(Proveedores.class, Where.equal("cif", a)));
            Proveedores p = prov.getFirst();
            auxprov = p;               
                
            }
              
                
                h.setStyle("-fx-background-color: slateblue; ");
        
        if (op == 1) {
            modProvCif.setText(String.valueOf(auxprov.getCif()));
            modProvDir.setText(auxprov.getDireccion());
            modProvMail.setText(String.valueOf(auxprov.getCorreo()));
            modProvTlf.setText(String.valueOf(auxprov.getTelefono()));

        } else {
            BajasProveedores(evt, auxprov);
        }

    }

    @FXML
    private void on_enter(Event evt) {
        KeyEvent e = (KeyEvent) evt;
        if (e.getCode() == KeyCode.ENTER) {
            String id = ((Node) evt.getSource()).getParent().getId();
            System.out.println(id);

            switch (id) {
                case "paneprodaltas":
                    onDarDeAltaProducto(evt);
                    break;
                case "paneprodmod":
                    on_modificar_productos(evt);
                    break;
                case "paneprovaltas":
                    onAltaProovedor(evt);
                    break;
                case "paneprovmod":
                    onModificarProovedor(evt);
                    break;
                case "paneclientesmod":
                    onModificarCliente(evt);
                    break;
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        combo_productos();
    }

}
