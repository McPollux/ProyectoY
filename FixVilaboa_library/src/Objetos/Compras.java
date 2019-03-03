package Objetos;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

import java.util.Set;

public class Compras implements Serializable {

    private int id;
    private Cuentas cuenta;
    private Clientes cliente;
    private Set<Pedidos> pedidos;
    private float precioTotal;
    private boolean formaPago;
    private Date fechaSolicitud;
    private boolean completado;

    public Compras(Clientes cliente, Cuentas cuenta, boolean formaPago) {
        this.cliente = cliente;
        this.cuenta = cuenta;
        this.pedidos = new HashSet<>();
        this.precioTotal= -1f;
        this.formaPago = formaPago;
        this.fechaSolicitud = new Date();
        this.completado = false;
    }

    public Compras() {
        this.pedidos = new HashSet<>();
        this.fechaSolicitud = new Date();
        this.completado = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }
    
    public Cuentas getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuentas cuenta) {
        this.cuenta = cuenta;
    }

    public Set<Pedidos> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedidos> pedidos) {
        this.pedidos = pedidos;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }

    public boolean isFormaPago() {
        return formaPago;
    }

    public void setFormaPago(boolean formaPago) {
        this.formaPago = formaPago;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

}
