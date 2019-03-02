package recursos;

import Objetos.*;
import java.util.*;
import org.hibernate.Session;
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
import proyectox_admin.NewHibernateUtil;
import proyectox_admin.ProyectoX_Admin;

/**
 *
 * @author a16jesusgc
 */
public class Busquedas {

    public static List<Proveedores> listaProveedores() {

        List<Proveedores> lista = new ArrayList();

        if (ProyectoX_Admin.gestor == 0) {

            Session sesion = NewHibernateUtil.getSession();

            lista = sesion.createCriteria(Proveedores.class).list();

            sesion.close();

        } else {

            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");

            Objects<Proveedores> provs = odb.getObjects(Proveedores.class);

            lista.addAll(provs);

        }

        return lista;
    }

    public static List<Productos> listaProductos() {

        List<Productos> lista = new ArrayList();

        if (ProyectoX_Admin.gestor == 0) {

            Session sesion = NewHibernateUtil.getSession();

            lista = sesion.createCriteria(Productos.class).list();

            sesion.close();

        } else {

            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");

            Objects<Productos> prods = odb.getObjects(Productos.class);

            lista.addAll(prods);

        }

        return lista;
    }

    public static List<Clientes> listaClientes() {

        List<Clientes> lista = new ArrayList();

        if (ProyectoX_Admin.gestor == 0) {

            Session sesion = NewHibernateUtil.getSession();

            lista = sesion.createCriteria(Clientes.class).list();

            sesion.close();

        } else {

            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");

            Objects<Clientes> clients = odb.getObjects(Clientes.class);

            lista.addAll(clients);

        }

        return lista;
    }

    public static boolean buscarUsuario(String usuario) {
        boolean repetido = false;

        if (ProyectoX_Admin.gestor == 0) {

            Session sesion = NewHibernateUtil.getSession();
            List<Usuarios> lista = sesion.createCriteria(Usuarios.class).list();
            for (Usuarios i : lista) {
                if (i.getUser().equalsIgnoreCase(usuario)) {
                    repetido = true;
                }
            }
            sesion.close();

        } else {

            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");

            Objects<Usuarios> lista = odb.getObjects(new CriteriaQuery(Usuarios.class, Where.equal("user", usuario)));

            if (!lista.isEmpty()) {
                repetido = true;
            }

        }

        return repetido;
    }

    public static int IDporNombre(String usuario) {
        int ID = 0;

        if (ProyectoX_Admin.gestor == 0) {

            Session sesion = NewHibernateUtil.getSession();
            List<Usuarios> lista = sesion.createCriteria(Usuarios.class).list();
            for (Usuarios i : lista) {
                if (i.getUser().equalsIgnoreCase(usuario)) {
                    ID = i.getId();
                }
            }
            sesion.close();

        } else {

            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");

            Objects<Usuarios> lista = odb.getObjects(new CriteriaQuery(Usuarios.class, Where.equal("user", usuario)));

            if (!lista.isEmpty()) {
                ID = lista.getFirst().getId();
            }

        }

        return ID;
    }

    public static boolean buscarProducto(String nombre) {
        boolean repetido = false;

        if (ProyectoX_Admin.gestor == 0) {

            Session sesion = NewHibernateUtil.getSession();
            List<Productos> lista = sesion.createCriteria(Productos.class).list();
            for (Productos i : lista) {
                if (i.getNombre().equalsIgnoreCase(nombre)) {
                    repetido = true;
                }
            }
            sesion.close();

        } else {

            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");

            Objects<Productos> lista = odb.getObjects(new CriteriaQuery(Productos.class, Where.equal("nombre", nombre)));

            if (!lista.isEmpty()) {
                repetido = true;
            }

        }

        return repetido;
    }

    public static int productoPorNombre(String nombre) {
        int ID = -1;

        if (ProyectoX_Admin.gestor == 0) {

            Session sesion = NewHibernateUtil.getSession();
            List<Productos> lista = sesion.createCriteria(Productos.class).list();
            for (Productos i : lista) {
                if (i.getNombre().equalsIgnoreCase(nombre)) {
                    ID = i.getId();
                }
            }
            sesion.close();

        } else {

            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");

            Objects<Productos> lista = odb.getObjects(new CriteriaQuery(Productos.class, Where.equal("nombre", nombre)));

            if (!lista.isEmpty()) {
                ID = lista.getFirst().getId();
            }

        }

        return ID;
    }

    public static Proveedores buscarProveedor(String cif) {

        Proveedores p = null;

        if (ProyectoX_Admin.gestor == 0) {

            Session sesion = NewHibernateUtil.getSession();
            List<Proveedores> lista = sesion.createCriteria(Proveedores.class).list();
            for (Proveedores i : lista) {
                if (i.getCif().equalsIgnoreCase(cif)) {
                    p = i;
                }
            }
            sesion.close();

        } else {
            
            ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");

            Objects<Proveedores> lista = odb.getObjects(new CriteriaQuery(Proveedores.class, Where.equal("cif", cif)));

            if (!lista.isEmpty()) {
                p = lista.getFirst();
            }

        }

        return p;
    }

}
