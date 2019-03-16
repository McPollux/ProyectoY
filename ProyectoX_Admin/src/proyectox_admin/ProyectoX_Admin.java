/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectox_admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.SessionFactory;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.ODBServer;
import recursos.*;

/**
 *
 * @author a16jesusgc
 */
public class ProyectoX_Admin extends Application {

    public static int gestor;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Log_in_Administradores.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {

            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/?user=root&password=usbw");
            Statement sentencia = conexion.createStatement();
            CrearBD.crear(sentencia);
            sentencia.close();

            SessionFactory sf = NewHibernateUtil.getSessionFactory();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        ODBServer server = ODBFactory.openServer(8000);
        server.addBase("proyectojjcv", "proyectojjcv.db");
        server.startServer(true);

        launch(args);
    }

}
