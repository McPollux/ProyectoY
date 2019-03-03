package Objetos;

import Objetos.Compras;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Clientes extends Usuarios implements Serializable {
    
    private String dni;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo;
    private Set<Compras> compras;
    private Set<Cuentas> cuentas;

    public Clientes(String user, int contrasenha, String dni, String nombre, String direccion, String telefono, String correo) {
        super(user, contrasenha);
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.compras = new HashSet<>();
        this.cuentas = new HashSet<>();
    }

    public Clientes() {
    }
    
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Set<Compras> getCompras() {
        return compras;
    }

    public void setCompras(Set<Compras> compras) {
        this.compras = compras;
    }

    public Set<Cuentas> getCuentas() {
        return cuentas;
    }

    public void setCuentas(Set<Cuentas> cuentas) {
        this.cuentas = cuentas;
    }
    
}
