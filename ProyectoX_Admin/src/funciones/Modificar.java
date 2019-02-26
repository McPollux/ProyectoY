package funciones;

import Objetos.*;
import excepciones.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import proyectox_admin.libreriadelpalo;
import proyectox_admin.ProyectoX_Admin;
import recursos.*;

/**
 *
 * @author JJCV
 */
public class Modificar {

    public static boolean proveedores(Proveedores p, String newdireccion, String newmail, String newtelefono) {
        boolean bien = false;
        String error = "";
        //HACER COMPROBACIONES

        if (newdireccion.isEmpty()) {
            //direccion mal, vacia
            bien = false;
            error = error + "\nLa dirección no puede estar vacía";
        }

        bien = Validar.telefonoSinLabel(newtelefono);
        //false si telefono no vale
        error = error + "\nEl teléfono no cumple los requisitos, deben ser 9 números";

        if (newmail.isEmpty()) {
            //email mal, vacio
            bien = false;
            error = error + "\nEl email no puede estar vacío";
        } else {
            if (!Validar.email(newmail)) {
                //email mal
                bien = false;
                error = error + "\nEl email no cumple formato de correo electrónico";
            }
        }

        if (bien) {
            byte r = libreriadelpalo.dialogo("Seguro que quieres modificar este proveedor?");
            if (r == -1) {
                System.out.println("Biennnn");

                p.setCorreo(newmail);
                p.setDireccion(newdireccion);
                p.setTelefono(newtelefono);

                if(ProyectoX_Admin.gestor == 0){
                    SesionOperation.modificar(p);
                }else{
                    SesionOperation.modificarNeo(p);
                }
                

                bien = true;
            } else {
                //Modificacion cancelada
                bien = false;
                error = error + "\nModificación cancelada";
            }
        } else {
            //error
            libreriadelpalo.dialogoinformacion(error);
        }

        return bien;
    }

    public static boolean productos(Productos p, String nombre, String descripcion, String precio, String stockmin, String stockactual, String stockmax, byte[] imagen) throws IOException {
        //HACER COMPROBACIONES

        boolean valido = true;
        String error = "ERROR";

        if (nombre.isEmpty()) {
            //Nombre vacio
            error += "\nEl campo nombre está vacío cuando es obligatorio";
            valido = false;
        } else {
            //Nombre válido, se pone el primer carácter en mayúscula
            nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
        }

        if (precio.isEmpty()) {
            //Precio vacio
            error += "\nEl campo precio está vacío cuando es obligatorio";
            valido = false;
        } else {
            if (!precio.matches("[0-9,.]*")) {
                //El precio debe ser un número decimal
                error += "\nEl campo precio debe ser un número decimal";
                valido = false;
            }
        }

        if (stockmin.isEmpty()) {
            //Precio vacio
            error += "\nEl campo stock mínimo está vacío cuando es obligatorio";
            valido = false;
        } else {
            if (!stockmin.substring(0).matches("[0-9]*")) {
                //El stock mínimo debe ser un número
                error += "\nEl stock mínimo debe ser un número";
                valido = false;
            }
            if (stockactual.isEmpty()) {
                //Precio vacio
                error += "\nEl campo stock actual está vacío cuando es obligatorio";
                valido = false;
            } else {
                if (!stockactual.substring(0).matches("[0-9]*")) {
                    //El stock actual debe ser un número
                    error += "\nEl stock actual debe ser un número";
                    valido = false;
                }
                if (stockmax.isEmpty()) {
                    //Precio vacio
                    error += "\nEl campo stock máximo está vacío cuando es obligatorio";
                    valido = false;
                } else {
                    if (!stockactual.substring(0).matches("[0-9]*")) {
                        //El stock actual debe ser un número
                        error += "\nEl stock actual debe ser un número";
                        valido = false;
                    } else {
                        if (Integer.parseInt(stockmin) >= Integer.parseInt(stockmax)) {
                            //El stock máximo no es mayor al mínimo
                            error += "\nEl stock máximo debe ser superior al stock mínimo";
                            valido = false;
                        } else {
                            if (!(Integer.parseInt(stockmin) <= Integer.parseInt(stockactual) && Integer.parseInt(stockactual) <= Integer.parseInt(stockmax))) {
                                //El stock actual debe ser un valor entre el stock mínimo y el máximo
                                error += "\nEl stock actual debe estar entre el mínimo y el máximo";
                                valido = false;
                            }
                        }
                    }

                }
            }
        }

        byte[] img = null;
        if (imagen != null) {
            img = imagen;
        } else {
            //Falta la imagen, se pone la imagen por defecto
            BufferedImage bufferimage = ImageIO.read(new File("src\\proyectoX\\imagenes\\default.jpg"));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(bufferimage, "jpg", output);
            img = output.toByteArray();
        }

        if (valido) {
            byte r = libreriadelpalo.dialogo("Seguro que quieres modificar este Producto?");
            if (r == -1) {
                System.out.println("Biennnn");
                //MODIFICAR
                p.setNombre(nombre);
                p.setDescripcion(descripcion);
                p.setPrecio(Float.parseFloat(precio));
				p.setStockMin(Integer.parseInt(stockmin));
                p.setStockActual(Integer.parseInt(stockactual));
                p.setStockMax(Integer.parseInt(stockmax));
                p.setImg(img);
                if(ProyectoX_Admin.gestor == 0){
                    SesionOperation.modificar(p);
                }else{
                    SesionOperation.modificarNeo(p);
                }
            }
        } else {
            //salta el error:
            libreriadelpalo.dialogoinformacion(error);
        }

        return valido;
    }

    public static boolean clientes(Clientes c, String nombre, String direccion, String telefono, String mail) throws IOException {
        //HACER COMPROBACIONES

        boolean valido = true;
        String error = "";

        if (nombre.isEmpty()) {
            //nombre mal, vacia
            valido = false;
            error = error + "\nLa dirección no puede estar vacía";
        } else {
            nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
        }

        if (direccion.isEmpty()) {
            //direccion mal, vacia
            valido = false;
            error = error + "\nLa dirección no puede estar vacía";
        }

        valido = Validar.telefonoSinLabel(telefono);
        //false si telefono no vale
        error = error + "\nEl teléfono no cumple los requisitos, deben ser 9 números";

        if (mail.isEmpty()) {
            //email mal, vacio
            valido = false;
            error = error + "\nEl email no puede estar vacío";
        } else {
            if (!Validar.email(mail)) {
                //email mal
                valido = false;
                error = error + "\nEl email no cumple formato de correo electrónico";
            }
        }

        if (valido) {
            byte r = libreriadelpalo.dialogo("Seguro que quieres modificar este Cliente?");
            if (r == -1) {
                System.out.println("Biennnn");
                //MODIFICAR
                c.setNombre(nombre);
                c.setDireccion(direccion);
                c.setTelefono(telefono);
                c.setCorreo(mail);

                if(ProyectoX_Admin.gestor == 0){
                    SesionOperation.modificar(c);
                }else{
                    SesionOperation.modificarNeo(c);
                }
            }
        } else {
            //salta el error:
            libreriadelpalo.dialogoinformacion(error);
        }

        return valido;
    }
}
