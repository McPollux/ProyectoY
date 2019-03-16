package funciones;

import Objetos.*;
import proyectox_admin.libreriadelpalo;
import recursos.SesionOperation;
import proyectox_admin.ProyectoX_Admin;

/**
 *
 * @author JJCV
 */
public class Bajas {

    public static boolean proveedores(Proveedores p) {
        boolean bien = false;
        //HACER COMPROBACIONES

        byte r = libreriadelpalo.dialogo("Seguro que quieres borrar este proveedor?");
        if (r == -1) {
            //INSERTAR

            if(ProyectoX_Admin.gestor == 0){
                SesionOperation.eliminar(p);
            }else{
                SesionOperation.eliminarNeo(p);
            }
            

            bien = true;
        } else {
            //Baja proveedor fallida
            bien = false;
        }

        return bien;
    }

    public static boolean clientes(Clientes c) {
        boolean bien = false;
        //HACER COMPROBACIONES

        byte r = libreriadelpalo.dialogo("Seguro que quieres borrar este cliente?");
        if (r == -1) {
            //INSERTAR

            if(ProyectoX_Admin.gestor == 0){
                SesionOperation.eliminar(c);
            }else{
                SesionOperation.eliminarNeo(c);
            }

            bien = true;
        } else {
            //Baja cliente fallida
            bien = false;
        }

        return bien;
    }
    
}
