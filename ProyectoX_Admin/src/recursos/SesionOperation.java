/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import org.hibernate.Session;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
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

        odb.delete(o);

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
    
    //método para modificar un objeto en Neodatis
    public static void modificarNeo(Object o) {
        ODB odb = ODBFactory.openClient("localhost", 8000, "proyectojjcv");
        
        odb.store(o);

        odb.commit();

        odb.close();
    }

}
