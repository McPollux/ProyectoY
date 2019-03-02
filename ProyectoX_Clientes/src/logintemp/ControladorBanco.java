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

/**
 *
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
        Session s = NewHibernateUtil.getSession();
        Clientes clienteActual = (Clientes) s.get(Clientes.class, LoginTemp.getClienteActual().getId());
        for (Cuentas cu : clienteActual.getCuentas()) {
            cmbCuenta.getItems().add(cu.getNumeroCuenta());
            cmbCuenta2.getItems().add(cu.getNumeroCuenta());
        }
        //cmbCuenta.getSelectionModel().select(cmbCuenta2.getSelectionModel().getSelectedIndex()); //Seleccionamos el mismo que este abierto en la otra ventana
        s.close();
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

    public void vaciar() {
        txtTitular.setText("");
        txtTarjeta.setText("");
        txtSaldo.setText("");
        txtCVV.setText("");
        txtCaducidad.setText("");
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
            for (Cuentas cu : cli.getCuentas()) {
                cmbCuenta.getItems().add(cu.getNumeroCuenta());
                cmbCuenta2.getItems().add(cu.getNumeroCuenta());
            }
            s.close();
            txtCuentaAlta.setText("");
            txtClienteAlta.setText("");
            txtSaldoAlta.setText("");
            txtTarjetaAlta.setText("");
            txtCVVAlta.setText("");
            txtFechaCadAlta.setText("");

        }
    }

    public void confirmarIngreso() {
        String mensaje = Validaciones.saldo(txtSaldoIng.getText());

        if (!mensaje.equals("")) {

            String numeroCuenta = cmbCuenta2.getSelectionModel().getSelectedItem();

            Session s = NewHibernateUtil.getSession();

            Cuentas c = (Cuentas) s.get(Cuentas.class, numeroCuenta);

            c.setSaldo(c.getSaldo() + Float.parseFloat(txtSaldoIng.getText()));

            s.beginTransaction();
            s.saveOrUpdate(c);
            s.getTransaction().commit();
            s.close();
        } else {
            lblAdvertencia2.setText(mensaje);
        }
    }

    public void seleccionAumentoSaldo() {
        txtSaldoIng.setText("");
        Session s = NewHibernateUtil.getSession();
        Clientes cli = (Clientes) s.get(Clientes.class, LoginTemp.getClienteActual().getId());

        for (Cuentas c : cli.getCuentas()) {
            if (c.getNumeroCuenta().equals(cmbCuenta2.getSelectionModel().getSelectedItem())) {
                lblSaldo.setText(String.valueOf(c.getSaldo()));
            }
        }
        s.close();
    }

    public void confAumentoSaldo() {
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
    }

    public void datosBanco() {
        Session s = NewHibernateUtil.getSession();
        Clientes cli = (Clientes) s.get(Clientes.class, LoginTemp.getClienteActual().getId());

        for (Cuentas c : cli.getCuentas()) {
            if (c.getNumeroCuenta().equals(cmbCuenta.getSelectionModel().getSelectedItem())) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
                txtTitular.setText(c.getCliente().getNombre());//El onjeto cuenta no tiene titular 
                txtTarjeta.setText(c.getTarjeta().getNumeroTarjeta());
                txtSaldo.setText(String.valueOf(c.getSaldo()));
                txtCVV.setText(String.valueOf(c.getTarjeta().getCvv()));
                txtCaducidad.setText(sdf.format(c.getTarjeta().getFechaCaducidad()));

            }
        }
        s.close();
    }

    public void eliminarCuenta() {
        byte b = 0;
        Cuentas cu = null;
        Tarjetas t = null;
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
            txtTitular.setText("");//El objeto cuenta no tiene titular 
            txtTarjeta.setText("");
            txtSaldo.setText("");
            txtCVV.setText("");
            txtCaducidad.setText("");
            s.beginTransaction();
            s.delete(cu);
            s.delete(t);
            s.getTransaction().commit();
            cmbCuenta.getItems().clear();
            cmbCuenta2.getItems().clear();
            Clientes clie = (Clientes) s.get(Clientes.class, LoginTemp.getClienteActual().getId());
            for (Cuentas cue : clie.getCuentas()) {
                cmbCuenta.getItems().add(cue.getNumeroCuenta());
                cmbCuenta2.getItems().add(cue.getNumeroCuenta());
            }
            cmbCuenta.getSelectionModel().select(-1);

        }

        s.close();
    }

}
