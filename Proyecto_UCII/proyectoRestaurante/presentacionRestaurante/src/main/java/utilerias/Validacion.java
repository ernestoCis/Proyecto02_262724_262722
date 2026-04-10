package utilerias;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class Validacion {

    public static String limpiarCadena(String texto) {
        if (texto == null) {
            return "";
        }

        return texto.trim();
    }

    public static boolean esNombreValido(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        return nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
    }

    public static boolean esApellidoPaternoValido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            return false;
        }

        return apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
    }

    public static boolean esApellidoMaternoValido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            return true;
        }

        return apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
    }

    public static boolean esTelefonoValido(String tel) {
        if (tel == null || tel.trim().isEmpty()) {
            return false;
        }

        return tel.matches("^\\d{10}$");
    }

    public static boolean esEmailValido(String email) {
        if (email == null || email.trim().isEmpty()) {
            return true;
        }

        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }

    public static boolean esCantidadValida(String texto, String unidad) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }

        if (unidad.equalsIgnoreCase("PIEZAS")) {
            return texto.matches("^\\d+$");
        }

        return texto.matches("^\\d+(\\.\\d+)?$");
    }

    public static boolean esPrecioValido(String precio) {
        if (precio == null || precio.trim().isEmpty()) {
            return false;
        }

        return precio.matches("^\\d+(\\.\\d{1,3})?$");
    }
}