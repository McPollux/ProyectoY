package excepciones;

import javafx.scene.control.Label;

/**
 *
 * @author a16jesusgc
 */
public class Validar {

    public static boolean dni(String dni) {
        boolean correcto = false;

        return correcto;
    }

    public static boolean telefono(String tlf, Label tlfError) {
        boolean correcto = false;
        
        try {
            if (tlf.length() != 9) {
                throw new Excepciones("El teléfono debe ser de 9 carácteres");
            } else {
                if (!tlf.substring(0).matches("[0-9]*")) {
                    throw new Excepciones("El teléfono debe ser un número");
                } else {
                    correcto = true;
                }
            }
        } catch (Excepciones e) {

            tlfError.setVisible(true);
            tlfError.setText(e.getMessage());

        }
        return correcto;
    }
    
    public static boolean telefonoSinLabel(String tlf) {
        boolean correcto = false;
        
        try {
            if (tlf.length() != 9) {
                throw new Excepciones("El teléfono debe ser de 9 carácteres");
            } else {
                if (!tlf.substring(0).matches("[0-9]*")) {
                    throw new Excepciones("El teléfono debe ser un número");
                } else {
                    correcto = true;
                }
            }
        } catch (Excepciones e) {
            
        }
        return correcto;
    }

    public static boolean email(String email) {
        String patern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(patern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

}
