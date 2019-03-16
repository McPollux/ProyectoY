package Objetos;

import com.mysql.jdbc.Blob;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;


public class Productos  implements Serializable {
    
    private int id;
    private int stockMin;
    private int stockMax;
    private int stockActual;
    private String nombre;
    private float precio;
    private Proveedores proveedor;
    private byte[] img;
    private String descripcion;

    public Productos(int stockMin, int stockMax, int stockActual, String nombre, float precio, Proveedores proovedores, byte[] img, String descripcion) {
        this.stockMin = stockMin;
        this.stockMax = stockMax;
        this.stockActual = stockActual;
        this.nombre = nombre;
        this.precio = precio;
        this.proveedor = proovedores;
        this.img = img;
        this.descripcion=descripcion;
    }

    public Productos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStockMin() {
        return stockMin;
    }

    public void setStockMin(int stockMin) {
        this.stockMin = stockMin;
    }

    public int getStockMax() {
        return stockMax;
    }

    public void setStockMax(int stockMax) {
        this.stockMax = stockMax;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int valorNuevo) {
        
        this.stockActual = valorNuevo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Proveedores getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedores proveedor) {
        this.proveedor = proveedor;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
  
}
