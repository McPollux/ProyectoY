package Objetos;

import Objetos.Productos;
import Objetos.Compras;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pedidos implements Serializable, PropertyChangeListener {

    private int id;
    private int cantidad;
    private float precioTotal;
    private Productos producto;
    private Compras compra;

    public Pedidos(int cantidad, float precioTotal, Productos producto, Compras compra) {
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.producto = producto;
        this.compra = compra;
    }

    public Pedidos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos productos) {
        this.producto = productos;
    }

    public Compras getCompra() {
        return compra;
    }

    public void setCompra(Compras compra) {
        this.compra = compra;
    }
    
    public final void actualizarStock(){
        producto.setStockActual(producto.getStockActual()-cantidad);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Date fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        File f = new File("fichero.txt");
        try {
            BufferedWriter bw= new BufferedWriter(new FileWriter(f));
            bw.write("Se ha realizado una actualizacion de stock del producto \""+producto.getNombre()+"\" en la fecha: "+sdf.format(fecha));
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Pedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        producto.setStockActual(producto.getStockMax());
    }

}
