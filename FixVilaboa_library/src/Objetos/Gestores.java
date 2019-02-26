package Objetos;

import java.io.Serializable;

public class Gestores extends Usuarios  implements Serializable {

    public Gestores() {
       super();
    }
    
    public Gestores(String nombre, int contrasenha){
        super(nombre,contrasenha);
    }
}
