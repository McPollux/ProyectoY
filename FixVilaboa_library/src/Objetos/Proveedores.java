package Objetos;

import java.io.Serializable;

public class Proveedores  implements Serializable {

    private String cif;
    private String direccion;
    private String telefono;
    private String correo;

    public Proveedores(){
    }
    
    public Proveedores(String cif, String direccion, String telefono, String correo) {
        this.cif = cif;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
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
}