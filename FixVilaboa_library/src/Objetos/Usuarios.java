package Objetos;

import java.io.Serializable;

/**
 *
 * @author a16cristiancc
 */
public abstract class Usuarios implements Serializable {
    
    private int id;
    private String user;
    private int contrasenha;
    
    public Usuarios(){
    }

    public Usuarios(String nombre, int contrasenha) {
        this.user = nombre;
        this.contrasenha = contrasenha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String nombre) {
        this.user = nombre;
    }

    public int getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(int contrasenha) {
        this.contrasenha = contrasenha;
    }
    
}
