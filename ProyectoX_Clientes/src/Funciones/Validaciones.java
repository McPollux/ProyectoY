package Funciones;

import Objetos.Clientes;
import com.jfoenix.controls.JFXPasswordField;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logintemp.NewHibernateUtil;
import org.hibernate.Session;

public class Validaciones {

    public static String dni(String dni) {
        String b = null;

        if (dni.length() != 9) {
            b = "El dni debe tener 8 digitos y una letra";
            return b;

        }
        if (!dni.substring(8).matches("[A-Za-z]")) {
            b = "El dni debe terminar por una letra";
            return b;

        }
        return "";

    }

    public static String telf(String telf) {
        String b = null;

        if (telf.length() != 9) {
            b = "El telf debe tener 9 digitos";
            return b;

        }
        if (!telf.substring(0, 1).matches("[0-9]*")) {
            b = "El telefono solo puede contener numeros";
            return b;

        }

        return "";
    }

    public static String usuario(String nombre) {
        String b = "";
        Session s = NewHibernateUtil.getSession();

        List<Clientes> clientes = s.createCriteria(Clientes.class).list();

        for (Clientes cli : clientes) {
            if (cli.getUser().equalsIgnoreCase(nombre)) {
                b = "Usuario ya registrado en la base de datos";

            }
        }

        s.close();
        return b;
    }

    public static String Contrasenha(JFXPasswordField txtPass, JFXPasswordField txtPass2) {
        String b = "";

        if (txtPass.getText().length() < 6) {
            b = "La contraseña debe contener al menos 6 caracteres";
            return b;
        }
        if (!txtPass.getText().equals(txtPass2.getText())) {
            b = "Las contraseñas no coinciden";
            return b;
        }
        return b;
    }
    
    public static boolean mail(String mail){
    
        String patern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(patern);
        java.util.regex.Matcher m = p.matcher(mail);
        return m.matches();
    
    }
    public static String numeroCuenta(String numero) {
        String b = "";
        if (numero.length() != 20) {
            b = "El número de cuenta debe tener 20 digitos";
        } else if (!numero.substring(0).matches("[0-9]*")) {
            b = "El numero de cuenta solo puede tener dígitos";
        }
        return b;
    }

    public static String saldo(String saldo) {
        String b = "";

        if (!saldo.matches("[0-9]*.")) {
            b = "El saldo solo puede tener dígitos";
        } else if (Float.parseFloat(saldo) < 0) {
            b = "El saldo debe ser positivo";
        } else if (!saldo.matches(".2f"));
        return b;
    }

    public static String numeroTarjeta(String numero) {
        String b = "";
        if (numero.length() != 16) {
            b = "El número de tarjeta debe tener por 16 digitos";
        } else if (!numero.matches("[0-9]*")) {
            b = "El numero de tarjeta solo puede tener dígitos";
        }
        return b;
    }

    public static String cvv(String cvv) {
        String b = "";
        if (cvv.length() != 3) {
            b = "El CVV debe estar compuesto por 3 dígitos";
        } else if (!cvv.matches("[0-9]*")) {
            b = "El numero de cuenta solo puede tener dígitos";
        }
        return b;
    }

    public static boolean fechaCaducidad(String fecha) {
        boolean b = true;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            sdf.parse(fecha);
        } catch (ParseException ex) {
            b=false;
        }
        return b;
    }

    
}
