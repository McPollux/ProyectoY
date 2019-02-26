/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author JJCV
 */
public class CrearBD {

 public static void crear(Statement sentencia) throws SQLException {
        Boolean b = false;
        
        try {
            sentencia.execute("Use proyectoJJCV");
        } catch (SQLException e) {
            b = true;
        }
        
        if (b) {  //en caso de que Use "nombreDeProyecto" falle, significa que la BD no está creada, por tanto ejecutamos las sentencias de creado
            sentencia.execute("CREATE DATABASE IF NOT EXISTS proyectoJJCV");
            sentencia.execute("Use  proyectoJJCV");

            sentencia.execute("CREATE TABLE IF NOT EXISTS usuarios(\n"
                    + "  ID INT(4) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,\n"
                    + "  USER VARCHAR(30) NOT NULL,\n"
                    + "  CONTRASENHA INT NOT NULL,\n"
                    + "  PRIMARY KEY (ID)\n"
                    + ");");

            sentencia.execute("CREATE TABLE IF NOT EXISTS clientes(\n"
                    + "  ID INT(4) UNSIGNED ZEROFILL NOT NULL, \n"
                    + "  DNI CHAR(9) NOT NULL,\n"
                    + "  NOMBRE VARCHAR(30) NOT NULL,\n"
                    + "  DIRECCION VARCHAR(30) NOT NULL,\n"
                    + "  TELEFONO CHAR(9) NOT NULL,\n"
                    + "  CORREO VARCHAR(30) NOT NULL,\n"
                    + "  PRIMARY KEY (ID),\n"
                    + "  FOREIGN KEY (ID) REFERENCES usuarios(ID)\n"
                    + "          ON DELETE CASCADE\n"
                    + "          ON UPDATE CASCADE,\n"
                    + "  INDEX fk1_cliente (ID)\n"
                    + ");");

            sentencia.execute("CREATE TABLE IF NOT EXISTS gestores(\n"
                    + "  ID INT(4) UNSIGNED ZEROFILL NOT NULL,\n"
                    + "  FOREIGN KEY (ID) REFERENCES usuarios(ID)\n"
                    + "          ON DELETE CASCADE\n"
                    + "          ON UPDATE CASCADE,\n"
                    + "  INDEX fk2_gestores (ID)\n"
                    + ");");

            sentencia.execute("CREATE TABLE IF NOT EXISTS tarjetas(\n"
                    + "  NUMEROTARJETA CHAR(16) NOT NULL,\n"
                    + "  FECHACADUCIDAD DATE NOT NULL,\n"
                    + "  CVV CHAR(3) NOT NULL,\n"
                    + "  PRIMARY KEY (NUMEROTARJETA)\n"
                    + ");");

            sentencia.execute("CREATE TABLE IF NOT EXISTS cuentas(\n"
                    + "  NUMEROCUENTA CHAR(20) NOT NULL,\n"
                    + "  SALDO FLOAT NOT NULL,\n"
                    + "  CLIENTE INT(4) UNSIGNED ZEROFILL NOT NULL,\n"
                    + "  TARJETA CHAR(16) NOT NULL,\n"
                    + "  PRIMARY KEY (NUMEROCUENTA),\n"
                    + "  FOREIGN KEY (CLIENTE) REFERENCES clientes(ID)\n"
                    + "          ON DELETE CASCADE\n"
                    + "          ON UPDATE CASCADE,\n"
                    + "  INDEX fk3_cliente (CLIENTE),\n"
                    + "  FOREIGN KEY (TARJETA) REFERENCES tarjetas(NUMEROTARJETA)\n"
                    + "          ON DELETE CASCADE\n"
                    + "          ON UPDATE CASCADE,\n"
                    + "  INDEX fk4_tarjetas (TARJETA)\n"
                    + ");");

            sentencia.execute("CREATE TABLE IF NOT EXISTS compras(\n"
                    + "  ID INT(4) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,\n"
                    + "  CUENTA CHAR(20) NOT NULL,\n"
                    + "  PRECIOTOTAL FLOAT NOT NULL,\n"
                    + "  CLIENTE INT(4) UNSIGNED ZEROFILL NOT NULL,\n"
                    + "  FORMAPAGO BOOLEAN NOT NULL,\n"
                    + "  FECHASOLICITUD DATE NOT NULL,\n"
                    + "  COMPLETADO BOOLEAN NOT NULL,\n"
                    + "  PRIMARY KEY (ID),\n"
                    + "  FOREIGN KEY (CLIENTE) REFERENCES clientes(ID)\n"
                    + "          ON DELETE CASCADE\n"
                    + "          ON UPDATE CASCADE,\n"
                    + "  INDEX fk123_cuentas (CLIENTE),\n"
                    + "  FOREIGN KEY (CUENTA) REFERENCES cuentas(NUMEROCUENTA)\n"
                    + "          ON DELETE CASCADE\n"
                    + "          ON UPDATE CASCADE,\n"
                    + "  INDEX fk5_cuentas (CUENTA)\n"
                    + ");");

            sentencia.execute("CREATE TABLE IF NOT EXISTS proveedores(\n"
                    + "  CIF VARCHAR(30) NOT NULL,\n"
                    + "  DIRECCION VARCHAR(30) NOT NULL,\n"
                    + "  TELEFONO CHAR(9) NOT NULL,\n"
                    + "  CORREO VARCHAR(30) NOT NULL,\n"
                    + "  PRIMARY KEY (CIF)\n"
                    + ");");

            sentencia.execute("CREATE TABLE IF NOT EXISTS productos(\n"
                    + "  ID INT(4) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,\n"
                    + "  STOCKMIN INT NOT NULL,\n"
                    + "  STOCKMAX INT NOT NULL,\n"
                    + "  STOCKACTUAL INT NOT NULL,\n"
                    + "  NOMBRE VARCHAR(30) NOT NULL,\n"
                    + "  PRECIO FLOAT NOT NULL,\n"
                    + "  PROVEEDOR VARCHAR(30) NULL,\n"
                    + "  IMG BLOB NULL,\n"
                    + "  DESCRIPCION VARCHAR(250) NULL,\n"
                    + "  PRIMARY KEY (ID),\n"
                    + "  FOREIGN KEY (PROVEEDOR) REFERENCES proveedores(CIF)\n"
                    + "          ON DELETE SET NULL\n"
                    + "          ON UPDATE CASCADE,\n"
                    + "  INDEX fk6_proveedores (PROVEEDOR)\n"
                    + ");");

            sentencia.execute("CREATE TABLE IF NOT EXISTS pedidos(\n"
                    + "  ID INT(4) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,\n"
                    + "  CANTIDAD INT NOT NULL,\n"
                    + "  PRECIOTOTAL FLOAT NOT NULL,\n"
                    + "  PRODUCTO INT(4) UNSIGNED ZEROFILL NOT NULL,\n"
                    + "  COMPRA INT(4) UNSIGNED ZEROFILL NOT NULL,\n"
                    + "  PRIMARY KEY (ID),\n"
                    + "  FOREIGN KEY (PRODUCTO) REFERENCES productos(ID)\n"
                    + "          ON DELETE CASCADE\n"
                    + "          ON UPDATE CASCADE,\n"
                    + "  INDEX fk7_productos (PRODUCTO),\n"
                    + "  FOREIGN KEY (COMPRA) REFERENCES compras(ID)\n"
                    + "          ON DELETE CASCADE\n"
                    + "          ON UPDATE CASCADE,\n"
                    + "  INDEX fk8_compras (COMPRA)\n"
                    + ");");
        }
    }
}
