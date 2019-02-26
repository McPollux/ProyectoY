package Objetos;

import java.io.Serializable;

public class Cuentas implements Serializable {

    private String numeroCuenta;
    private float saldo;
    private Clientes cliente;
    private Tarjetas tarjeta;
    
    
    public Cuentas(String numero, float saldo, Clientes cliente, Tarjetas tarjeta) {
        this.numeroCuenta = numero;
        this.saldo = saldo;
        this.cliente = cliente;
        this.tarjeta = tarjeta;
    }

    public Cuentas() {
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numero) {
        this.numeroCuenta = numero;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Tarjetas getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjetas tarjeta) {
        this.tarjeta = tarjeta;
    }

}
