/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import Objetos.*;
import org.hibernate.Session;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.OID;
import org.neodatis.odb.core.oid.OIDFactory;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import proyectox_admin.NewHibernateUtil;

/**
 *
 * @author a16jesusgc
 */
public class SesionOperation {

    //método para dar de alta un objeto en Hibernate
    public static void insertar(Object o) {
        Session sesion = NewHibernateUtil.getSession();

        sesion.beginTransaction();
        sesion.saveOrUpdate(o);
        sesion.getTransaction().commit();

        sesion.close();
    }

    //método para dar de alta un objeto en Neodatis
    public static void insertarNeo(Object o) {
        ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
        
        if(o instanceof Productos){
            o = asignarID((Productos)(o));
        }

        odb.store(o);

        odb.commit();

        odb.close();
    }

    //método para dar de baja un objeto
    public static void eliminar(Object o) {
        Session sesion = NewHibernateUtil.getSession();

        sesion.beginTransaction();
        sesion.delete(o);
        sesion.getTransaction().commit();

        sesion.close();
    }

    //método para dar de baja un objeto en Neodatis
    public static void eliminarNeo(Object o) {
        ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
        
        if(o instanceof Productos){
            
            Objects<Productos> p = odb.getObjects(new CriteriaQuery(Productos.class, Where.equal("id", ((Productos) o).getId())));
            
            odb.delete(p.getFirst());
            
        } else if(o instanceof Proveedores){
            
            Objects<Proveedores> p = odb.getObjects(new CriteriaQuery(Proveedores.class, Where.equal("cif", ((Proveedores) o).getCif())));
            
            odb.delete(p.getFirst());
            
        } else if(o instanceof Clientes){
            
            Objects<Clientes> c = odb.getObjects(new CriteriaQuery(Clientes.class, Where.equal("id", ((Clientes) o).getId())));
            
            odb.delete(c.getFirst());
            
        }

        odb.commit();

        odb.close();
    }

    //método para modificar un objeto
    public static void modificar(Object o) {
        Session sesion = NewHibernateUtil.getSession();

        sesion.beginTransaction();
        sesion.saveOrUpdate(o);
        sesion.getTransaction().commit();

        sesion.close();
    }

    public static Productos asignarID(Productos p) {
        ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");

        Objects<Productos> prods = odb.getObjects(Productos.class);
        
        for (Productos prod : prods) {
            p.setId(prod.getId()+1);
        }
        
        return p;
    }

}
