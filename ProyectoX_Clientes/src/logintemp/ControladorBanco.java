/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logintemp;

import Objetos.Cuentas;
import Recursos.Inserciones;

import Recursos.Windows;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import java.util.List;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import Objetos.*;

import java.sql.Date;

import Funciones.Validaciones;
import com.jfoenix.controls.JFXTabPane;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicReference;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

/**
 * @author horudi
 */
public class ControladorBanco implements Initializable {

    private final Windows win = new Windows();
    @FXML
    private JFXTabPane panel;
    @FXML
    private JFXComboBox<String> cmbCuenta;

    @FXML
    private JFXComboBox<String> cmbCuenta2;

    @FXML
    private Label txtTitular;

    @FXML
    private Label txtTarjeta;

    @FXML
    private Label txtSaldo;

    @FXML
    private Label txtCVV;

    @FXML
    private Label txtCaducidad;

    @FXML
    private Label lblSaldo;

    @FXML
    private JFXTextField txtSaldoIng;

    @FXML
    private JFXTextField txtCuentaAlta;

    @FXML
    private JFXTextField txtSaldoAlta;

    @FXML
    private JFXTextField txtTarjetaAlta;

    @FXML
    private JFXTextField txtCVVAlta;

    @FXML
    private JFXTextField txtFechaCadAlta;
    @FXML
    private JFXTextField txtClienteAlta;

    @FXML
    private ImageView imgCaducidad;

    @FXML
    private ImageView imgCVV;

    @FXML
    private ImageView imgTarjeta;

    @FXML
    private ImageView imgSaldo;

    @FXML
    private ImageView imgCliente;

    @FXML
    private ImageView imgCuenta;

    @FXML
    private Label lblAdvertencia;

    @FXML
    private Label lblAdvertencia2;

    @FXML
    public void tienda() throws IOException {
        win.abrirVentana("/logintemp/tienda.fxml");
        win.cerrarVentana(txtTitular);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MoverVentanas(panel);
        vaciar(); //Vaciamos los label de la pestaña Cuentas

        if (LoginTemp.bbdd == 0) {
            Session s = NewHibernateUtil.getSession();
            Clientes clienteActual = (Clientes) s.get(Clientes.class, LoginTemp.getClienteActual().getId());

            cargarCombos(clienteActual);
            //cmbCuenta.getSelectionModel().select(cmbCuenta2.getSelectionModel().getSelectedIndex()); //Seleccionamos el mismo que este abierto en la otra ventana
            s.close();
        } else {
            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
            Clientes cliente = ODBCliente(odb);

            cargarCombos(cliente);
            odb.close();
        }
    }

    private Clientes ODBCliente(ODB odb) {  // Metodo utilizado a lo largo de la clase para obtener el cliente actual de la BBDD
        CriteriaQuery criteria = new CriteriaQuery(Clientes.class, Where.equal("dni", LoginTemp.getClienteActual().getDni()));
        Objects<Clientes> cli = odb.getObjects(criteria);
        return cli.getFirst();
    }

    private void cargarCombos(Clientes clienteActual) {  // Metodo utilizado a lo largo de la clase para cargar los combos con cuentas
        for (Cuentas cu : clienteActual.getCuentas()) {
            cmbCuenta.getItems().add(cu.getNumeroCuenta());
            cmbCuenta2.getItems().add(cu.getNumeroCuenta());
        }
    }

    public void vaciar() {
        txtTitular.setText("");
        txtTarjeta.setText("");
        txtSaldo.setText("");
        txtCVV.setText("");
        txtCaducidad.setText("");
    }

    private void vaciarAlta() {
        txtCuentaAlta.setText("");
        txtClienteAlta.setText("");
        txtSaldoAlta.setText("");
        txtTarjetaAlta.setText("");
        txtCVVAlta.setText("");
        txtFechaCadAlta.setText("");
    }

    private void MoverVentanas(JFXTabPane root) {

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

    @FXML
    public void altaCuenta() throws ParseException {
        boolean b = true;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if (!Validaciones.fechaCaducidad(txtFechaCadAlta.getText())) {
            b = false;
            imgCaducidad.setVisible(true);
            lblAdvertencia.setText("Formato de fecha inválido");
        } else {
            imgCaducidad.setVisible(false);
        }
        //
        String mensaje = Validaciones.cvv(txtCVVAlta.getText());
        if (!mensaje.equals("")) {
            b = false;
            imgCVV.setVisible(true);
        } else {
            imgCVV.setVisible(false);
        }
        //
        mensaje = Validaciones.numeroTarjeta(txtTarjetaAlta.getText());
        if (!mensaje.equals("")) {
            b = false;
            imgTarjeta.setVisible(true);
            lblAdvertencia.setText(mensaje);
        } else {
            imgTarjeta.setVisible(false);
        }
        //
        mensaje = Validaciones.saldo(txtSaldoAlta.getText());
        if (!mensaje.equals("")) {
            b = false;
            imgSaldo.setVisible(true);
            lblAdvertencia.setText(mensaje);
        } else {
            imgSaldo.setVisible(false);
        }
        //
        mensaje = Validaciones.numeroCuenta(txtCuentaAlta.getText());
        if (!mensaje.equals("")) {
            b = false;
            imgCuenta.setVisible(true);
            lblAdvertencia.setText(mensaje); //crear label
        } else {
            imgCuenta.setVisible(false);
        }
        if (b) {

            if (LoginTemp.bbdd == 0) {
                Session s = NewHibernateUtil.getSession();
                Clientes clienteActual = (Clientes) s.get(Clientes.class, LoginTemp.getClienteActual().getId());

                Tarjetas tarjeta = new Tarjetas(txtTarjetaAlta.getText(), sdf.parse(txtFechaCadAlta.getText()), txtCVVAlta.getText());
                Cuentas cuenta = new Cuentas(txtCuentaAlta.getText(), Float.parseFloat(txtSaldoAlta.getText()), clienteActual, tarjeta);

                clienteActual.getCuentas().add(cuenta);
                tarjeta.setCuenta(cuenta);

                s.beginTransaction();
                s.save(tarjeta);
                s.save(cuenta);
                s.saveOrUpdate(clienteActual);
                s.getTransaction().commit();

                cmbCuenta.getItems().clear();
                cmbCuenta2.getItems().clear();
                Clientes cli = (Clientes) s.get(Clientes.class, LoginTemp.getClienteActual().getId());
                cargarCombos(cli);
                s.close();
            } else {
                ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
                Clientes cliente = ODBCliente(odb);

                Tarjetas tarjeta = new Tarjetas(txtTarjetaAlta.getText(), sdf.parse(txtFechaCadAlta.getText()), txtCVVAlta.getText());
                Cuentas cuenta = new Cuentas(txtCuentaAlta.getText(), Float.parseFloat(txtSaldoAlta.getText()), cliente, tarjeta);

                cliente.getCuentas().add(cuenta);
                tarjeta.setCuenta(cuenta);

                cmbCuenta.getItems().clear();
                cmbCuenta2.getItems().clear();
                cargarCombos(cliente);

                odb.store(cliente);
                odb.commit();
                odb.close();
            }
            vaciarAlta();
        }
    }

    public void confirmarIngreso() {
        String mensaje = Validaciones.saldo(txtSaldoIng.getText());

        if (!mensaje.equals("")) {

            String numeroCuenta = cmbCuenta2.getSelectionModel().getSelectedItem();

            if (LoginTemp.bbdd == 0) {
                Session s = NewHibernateUtil.getSession();

                Cuentas c = (Cuentas) s.get(Cuentas.class, numeroCuenta);

                c.setSaldo(c.getSaldo() + Float.parseFloat(txtSaldoIng.getText()));

                s.beginTransaction();
                s.saveOrUpdate(c);
                s.getTransaction().commit();
                s.close();
            } else {
                ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
                CriteriaQuery criteria = new CriteriaQuery(Cuentas.class, Where.iequal("numero", numeroCuenta));
                Objects<Cuentas> cuentasObjects = odb.getObjects(criteria);

                cuentasObjects.getFirst().setSaldo(cuentasObjects.getFirst().getSaldo() + Float.parseFloat(txtSaldoIng.getText()));

                odb.store(cuentasObjects.getFirst());
                odb.commit();
                odb.close();
            }
        } else {
            lblAdvertencia2.setText(mensaje);
        }
    }

    public void seleccionAumentoSaldo() {
        txtSaldoIng.setText("");
        if (LoginTemp.bbdd == 0) {
            Session s = NewHibernateUtil.getSession();
            Clientes cli = (Clientes) s.get(Clientes.class, LoginTemp.getClienteActual().getId());

            for (Cuentas c : cli.getCuentas()) {
                if (c.getNumeroCuenta().equals(cmbCuenta2.getSelectionModel().getSelectedItem())) {
                    lblSaldo.setText(String.valueOf(c.getSaldo()));
                }
            }
            s.close();
        } else {
            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
            Clientes cliente = ODBCliente(odb);

            for (Cuentas c : cliente.getCuentas()) {
                if (c.getNumeroCuenta().equals(cmbCuenta2.getSelectionModel().getSelectedItem())) {
                    lblSaldo.setText(String.valueOf(c.getSaldo()));
                }
            }
            odb.close();
        }
    }

    public void confAumentoSaldo() {
        if (LoginTemp.bbdd == 0) {
            Session s = NewHibernateUtil.getSession();
            Clientes cli = (Clientes) s.get(Clientes.class, LoginTemp.getClienteActual().getId());

            for (Cuentas c : cli.getCuentas()) {
                if (c.getNumeroCuenta().equals(cmbCuenta2.getSelectionModel().getSelectedItem())) {
                    c.setSaldo(Float.parseFloat(lblSaldo.getText()) + Float.parseFloat(txtSaldoIng.getText()));
                    lblSaldo.setText(String.valueOf(c.getSaldo()));
                    s.beginTransaction();
                    s.saveOrUpdate(c);
                    s.getTransaction().commit();
                }
            }
            txtSaldoIng.setText("");
            s.close();
        } else {
            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
            Clientes cliente = ODBCliente(odb);
            for (Cuentas c : cliente.getCuentas()) {
                if (c.getNumeroCuenta().equals(cmbCuenta2.getSelectionModel().getSelectedItem())) {
                    c.setSaldo(Float.parseFloat(lblSaldo.getText()) + Float.parseFloat(txtSaldoIng.getText()));
                    lblSaldo.setText(String.valueOf(c.getSaldo()));
                    odb.store(cliente);
                    odb.commit();

                }
            }
            txtSaldoIng.setText("");
            odb.close();
        }
    }

    public void datosBanco() {
        if (LoginTemp.bbdd == 0) {
            Session s = NewHibernateUtil.getSession();
            Clientes cli = (Clientes) s.get(Clientes.class, LoginTemp.getClienteActual().getId());

            cargarDatos(cli);

            s.close();
        } else {
            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
            Clientes cliente = ODBCliente(odb);
            cargarDatos(cliente);
            odb.close();
        }
    }

    private void cargarDatos(Clientes cli) {  // Metodo creado para cargar el ciente actual de datosBanco()
        for (Cuentas c : cli.getCuentas()) {
            if (c.getNumeroCuenta().equals(cmbCuenta.getSelectionModel().getSelectedItem())) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
                txtTitular.setText(c.getCliente().getNombre());
                txtTarjeta.setText(c.getTarjeta().getNumeroTarjeta());
                txtSaldo.setText(String.valueOf(c.getSaldo()));
                txtCVV.setText(String.valueOf(c.getTarjeta().getCvv()));
                txtCaducidad.setText(sdf.format(c.getTarjeta().getFechaCaducidad()));
            }
        }
    }


    public void eliminarCuenta() {
        byte b = 0;
        Cuentas cu = null;
        Tarjetas t = null;

        if (LoginTemp.bbdd == 0) {
            Session s = NewHibernateUtil.getSession();
            Clientes cli = (Clientes) s.get(Clientes.class, LoginTemp.getClienteActual().getId());

            for (Cuentas c : cli.getCuentas()) {
                if (c.getNumeroCuenta().equals(cmbCuenta.getSelectionModel().getSelectedItem())) {
                    Windows win = new Windows();
                    b = win.dialogo("¿Desea eliminar esta cuenta?");
                    cu = c;
                    t = c.getTarjeta();

                }
            }
            if (b == -1) {
                cli.getCuentas().remove(cu);
                vaciar();
                s.beginTransaction();
                s.delete(cu);
                s.delete(t);
                s.getTransaction().commit();
                cmbCuenta.getItems().clear();
                cmbCuenta2.getItems().clear();
                Clientes clie = (Clientes) s.get(Clientes.class, LoginTemp.getClienteActual().getId());
                cargarCombos(clie);
                cmbCuenta.getSelectionModel().select(-1);
            }

            s.close();
        } else {
            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
            Clientes cliente = ODBCliente(odb);

            for (Cuentas c : cliente.getCuentas()) {
                if (c.getNumeroCuenta().equals(cmbCuenta.getSelectionModel().getSelectedItem())) {
                    Windows win = new Windows();
                    b = win.dialogo("¿Desea eliminar esta cuenta?");
                    cu = c;
                    t = c.getTarjeta();

                }
            }
            if (b == -1) {
                cliente.getCuentas().remove(cu);
                vaciar();
                odb.delete(cu);
                odb.delete(t);
                odb.commit();
                cmbCuenta.getItems().clear();
                cmbCuenta2.getItems().clear();
                cargarCombos(cliente);
                cmbCuenta.getSelectionModel().select(-1);
            }

            odb.close();
        }
    }

}
