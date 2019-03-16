package logintemp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Objetos.Clientes;
import Objetos.Compras;
import Recursos.CrearBD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.ODBServer;

/**
 *
 * @author jordi
 */
public class LoginTemp extends Application {

    public static int bbdd;
    public static Stage st;
    private static Clientes clienteActual;
    public static Compras cesta = new Compras();

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        st = stage;
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3307/?user=root&password=usbw";
        Connection c = DriverManager.getConnection(url);
        Statement s = c.createStatement();
        CrearBD.crear(s);
        NewHibernateUtil.getSessionFactory();
        ODBServer server = ODBFactory.openServer(8000);
        server.addBase("proyectojjcv", "../proyectojjcv.db");
        server.startServer(true);
        launch(args);

    }

    public static Clientes getClienteActual() {
        return clienteActual;
    }

    public static void setClienteActual(Clientes cl) {
        clienteActual = cl;
    }

}
