package Objetos;

import Objetos.Cuentas;
import java.io.Serializable;
import java.util.Date;

public class Tarjetas implements Serializable  {

    private String numeroTarjeta;
    private Date fechaCaducidad;
    private String cvv;
    private Cuentas cuenta;
    
    public Tarjetas(){
        
    }

    public Tarjetas(String numero, Date fechaCaducidad, String cvv) {
        this.numeroTarjeta = numero;
        this.fechaCaducidad = fechaCaducidad;
        this.cvv = cvv;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numero) {
        this.numeroTarjeta = numero;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Cuentas getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuentas cuenta) {
        this.cuenta = cuenta;
    }
    
}
