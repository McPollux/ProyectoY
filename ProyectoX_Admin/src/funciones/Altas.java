/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funciones;

import Objetos.*;
import com.mysql.jdbc.Blob;
import excepciones.Validar;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.Label;
import proyectox_admin.libreriadelpalo;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import org.hibernate.Session;
import proyectox_admin.NewHibernateUtil;
import recursos.*;
import proyectox_admin.ProyectoX_Admin;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.ObjectValues;
import org.neodatis.odb.Objects;
import org.neodatis.odb.Values;
import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

/**
 *
 * @author a16pablovc
 */
public class Altas {

    public static boolean login(String usuario, String contraseña, Label usuarioError, Label contraseñaError) {
        boolean bien = true;
        //COMPROBACIONES

        usuarioError.setVisible(false);
        contraseñaError.setVisible(false);

        if (usuario.isEmpty()) {
            //mal, usuario vacio
            bien = false;
            usuarioError.setVisible(true);
            usuarioError.setText("El campo usuario está\nvacío cuando es\nobligatorio");
        } else {
            if (contraseña.isEmpty()) {
                //mal, contraseña vacia
                bien = false;
                contraseñaError.setVisible(true);
                contraseñaError.setText("El campo contraseña está\nvacío cuando es\nobligatorio");
            } else {
                if (Busquedas.buscarUsuario(usuario)) {

                    int id = Busquedas.IDporNombre(usuario);

                    Gestores g;

                    if (ProyectoX_Admin.gestor == 0) {

                        Session sesion = NewHibernateUtil.getSession();

                        g = (Gestores) sesion.get(Gestores.class, id);

                        sesion.close();

                    } else {

                        ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");

                        Objects<Gestores> gestor = odb.getObjects(new CriteriaQuery(Gestores.class, Where.equal("id", id)));

                        g = gestor.getFirst();

                    }

                    if (g != null) {
                        if (g.getContrasenha() == (contraseña.hashCode())) {
                            //Usuario y contraseña correctos, loguea
                            bien = true;
                        } else {
                            //Contraseña incorrecta
                            contraseñaError.setVisible(true);
                            contraseñaError.setText("Contraseña incorrecta");
                            bien = false;
                        }

                    } else {
                        //Usuario inexistente
                        bien = false;
                        usuarioError.setVisible(true);
                        usuarioError.setText("No hay ningún\nadministrador con ese\nnombre de usuario");
                    }

                } else {
                    //Usuario inexistente
                    bien = false;
                    usuarioError.setVisible(true);
                    usuarioError.setText("No hay ningún usuario\ncon ese nombre");
                }
            }
        }

        return bien;
    }

    public static boolean administradores(String usuario, String contraseña, String repcontraseña, Label usuarioError, Label contraseñaError, Label repcontraseñaError) {
        boolean bien = false;
        //HACER COMPROBACIONES

        usuarioError.setVisible(false);
        contraseñaError.setVisible(false);
        repcontraseñaError.setVisible(false);

        if (usuario.isEmpty()) {
            //mal, usuario vacio
            usuarioError.setVisible(true);
            usuarioError.setText("El campo usuario está\nvacío cuando es obligatorio");
        } else {
            if (Busquedas.buscarUsuario(usuario)) {
                //mal, el usuario ya existe
                usuarioError.setVisible(true);
                usuarioError.setText("Ya existe un usuario\ncon ese nombre");
            } else {
                if (contraseña.isEmpty()) {
                    //mal, contraseña vacia
                    contraseñaError.setVisible(true);
                    contraseñaError.setText("El campo contraseña está\nvacío cuando es obligatorio");
                } else {
                    if (contraseña.length() < 6) {
                        //mal, contraseña no cumple requisitos
                        contraseñaError.setVisible(true);
                        contraseñaError.setText("La contraseña debe\ntener mínimo 6 caracteres");
                    } else {
                        if (contraseña.hashCode() != repcontraseña.hashCode()) {
                            //mal, las contraseñas no coinciden
                            repcontraseñaError.setVisible(true);
                            repcontraseñaError.setText("Las contraseñas deben coincidir");
                        } else {
                            //cumple los requisitos
                            bien = true;
                        }
                    }
                }
            }
        }

        if (bien) {
            byte r = libreriadelpalo.dialogo("Seguro que quieres registrar este Administrador");
            if (r == -1) {
                //INSERTAR

                Usuarios g = new Gestores(usuario, contraseña.hashCode());

                SesionOperation.insertar(g);
                SesionOperation.insertarNeo(g);

                bien = true;
            }
        } else {
            //Alta administrador fallida
        }

        return bien;
    }

    public static boolean proovedor(String cif, String direccion, String tlf, String mail, Label cifError, Label direccionError, Label tlfError, Label mailError) {
        //HACER COMPROBACIONES

        cifError.setVisible(false);
        direccionError.setVisible(false);
        tlfError.setVisible(false);
        mailError.setVisible(false);
        boolean bien = false;

        Session sesion = NewHibernateUtil.getSession();

        boolean valido = true;

        if (cif.isEmpty()) {
            //cif mal, vacio
            valido = false;
            cifError.setVisible(true);
            cifError.setText("Este campo está\nvacío cuando es\nobligatorio");
        } else {

            cif = cif.substring(0, 1).toUpperCase() + cif.substring(1);

            if (ProyectoX_Admin.gestor == 0) {

                Proveedores p = (Proveedores) sesion.get(Proveedores.class, cif);

                if (p != null) {
                    //cif mal, ya existe
                    valido = false;
                    cifError.setVisible(true);
                    cifError.setText("Ya existe un\nproveedor con\nese CIF");

                }

            } else {

                ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");

                Objects<Proveedores> lista = odb.getObjects(new CriteriaQuery(Proveedores.class, Where.equal("cif", cif)));

                if (!lista.isEmpty()) {

                    //cif mal, ya existe
                    valido = false;
                    cifError.setVisible(true);
                    cifError.setText("Ya existe un\nproveedor con\nese CIF");

                }

                odb.close();

            }

        }

        if (direccion.isEmpty()) {
            //direccion mal, vacia
            valido = false;
            direccionError.setVisible(true);
        }

        if (!Validar.telefono(tlf, tlfError)) {
            //telefono mal
            valido = false;
        }

        if (mail.isEmpty()) {
            //email mal, vacio
            valido = false;
            mailError.setVisible(true);
            mailError.setText("Este campo está\nvacío cuando es\nobligatorio");
        } else {
            if (!Validar.email(mail)) {
                //email mal
                valido = false;
                mailError.setVisible(true);
                mailError.setText("Introduzca formato email");
            }
        }

        if (valido) {
            byte r = libreriadelpalo.dialogo("Seguro que quieres dar de alta este Provedor");
            if (r == -1) {
                //INSERTAR
                Proveedores p = new Proveedores(cif, direccion, tlf, mail);

                if (ProyectoX_Admin.gestor == 0) {
                    SesionOperation.insertar(p);
                } else {
                    SesionOperation.insertarNeo(p);
                }

                bien = true;

            }
        }

        Busquedas.listaProveedores();

        return bien;
    }

    public static boolean productos(String nombre, String proveedor, String precio, String stockmin, String stockactual, String stockmax, String descripcion, File imagen) throws IOException {
        //HACER COMPROBACIONES

        boolean valido = true;
        String error = "ERROR";

        if (nombre.isEmpty()) {
            //Nombre vacio
            error += "\nEl campo nombre está vacío cuando es obligatorio";
            valido = false;
        } else {
            
            nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
            
            if (Busquedas.buscarProducto(nombre)) {
                //Ya existe un producto con ese nombre
                error += "\nYa existe ese producto";
                valido = false;
            }
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

        if (descripcion != null) {
            descripcion = descripcion.substring(0, 1).toUpperCase() + descripcion.substring(1);
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

        Proveedores prov = null;

        if (proveedor != null) {
            prov = Busquedas.buscarProveedor(proveedor);

            if (prov == null) {
                //Error al buscar el proveedor
                error += "\nNo se ha encontrado el proveedor";
                valido = false;
            }
        }

        byte[] img = null;

        if (imagen != null) {
            BufferedImage bufferimage = ImageIO.read(new File(imagen.toURI()));
            BufferedImage resized = Resize.resize(bufferimage);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(resized, "jpg", output);
            img = output.toByteArray();
        } else {
            //Falta la imagen, se pone la imagen por defecto
            BufferedImage bufferimage = ImageIO.read(new File("src\\proyectoX\\imagenes\\default.jpg"));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(bufferimage, "jpg", output);
            img = output.toByteArray();
        }

        if (valido) {
            byte r = libreriadelpalo.dialogo("Seguro que quieres dar de alta este Prooducto");
            if (r == -1) {
                //INSERTAR

                Productos p = new Productos(Integer.parseInt(stockmin), Integer.parseInt(stockmax), Integer.parseInt(stockactual), nombre, Float.parseFloat(precio), prov, img, descripcion);

                if (ProyectoX_Admin.gestor == 0) {
                    SesionOperation.insertar(p);
                } else {
                    SesionOperation.insertarNeo(p);
                }

            }
        } else {
            //salta el error:
            libreriadelpalo.dialogoinformacion(error);
        }

        return valido;
    }

}
