package utilerias;

/**
 * Clase utilitaria que centraliza las reglas de validación de datos del
 * sistema.
 * <p>
 * Proporciona métodos estáticos basados en expresiones regulares (Regex) para
 * verificar formatos de nombres, contactos, correos y valores numéricos.</p>
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class Validacion {

    /**
     * Elimina los espacios en blanco al inicio y al final de una cadena.
     *
     * @param texto La cadena a procesar.
     * @return La cadena limpia, o una cadena vacía si el parámetro es null.
     */
    public static String limpiarCadena(String texto) {
        if (texto == null) {
            return "";
        }

        return texto.trim();
    }

    /**
     * Valida que el nombre contenga únicamente letras (incluyendo acentos y ñ)
     * y espacios.
     *
     * @param nombre Cadena con el nombre a validar.
     * @return true si cumple con el formato, false si es null, vacío o contiene
     * caracteres inválidos.
     */
    public static boolean esNombreValido(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        return nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
    }

    /**
     * Valida que el apellido paterno cumpla con el formato alfabético.
     * <p>
     * Este no campo se considera opcional.</p>
     *
     * @param apellido Cadena con el apellido a validar.
     * @return La cadena del apellido paterno.
     */
    public static boolean esApellidoPaternoValido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            return false;
        }

        return apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
    }

    /**
     * Valida que el apellido materno cumpla con el formato alfabético.
     * <p>
     * A diferencia del apellido paterno, este campo se considera opcional
     * (retorna true si está vacío).</p>
     *
     * @param apellido Cadena con el apellido a validar.
     * @return true si es válido o está vacío; false si contiene caracteres no
     * permitidos.
     */
    public static boolean esApellidoMaternoValido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            return true;
        }

        return apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
    }

    /**
     * Verifica que el teléfono conste exactamente de 10 dígitos numéricos.
     *
     * @param tel Cadena del teléfono.
     * @return true si contiene exactamente 10 números, false en caso contrario.
     */
    public static boolean esTelefonoValido(String tel) {
        if (tel == null || tel.trim().isEmpty()) {
            return false;
        }

        return tel.matches("^\\d{10}$");
    }

    /**
     * Valida la estructura de un correo electrónico.
     * <p>
     * El campo es opcional. Si se proporciona, debe cumplir con el estándar
     * {@code usuario@dominio.extension}.</p>
     *
     * @param email Cadena del correo electrónico.
     * @return true si es válido o vacío; false si el formato es incorrecto.
     */
    public static boolean esEmailValido(String email) {
        if (email == null || email.trim().isEmpty()) {
            return true;
        }

        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }

    /**
     * Valida si una cantidad ingresada es congruente con su unidad de medida.
     * <p>
     * Si la unidad es "PIEZAS", solo acepta números enteros. Para otras
     * unidades, acepta números decimales.</p>
     *
     * @param texto Valor numérico en formato String.
     * @param unidad Tipo de medida (ej. "PIEZAS", "KG", "LITROS").
     * @return true si el número corresponde al formato de la unidad; false de
     * lo contrario.
     */
    public static boolean esCantidadValida(String texto, String unidad) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }

        if (unidad.equalsIgnoreCase("PIEZAS")) {
            return texto.matches("^\\d+$");
        }

        return texto.matches("^\\d+(\\.\\d+)?$");
    }

    /**
     * Verifica que el precio sea un número positivo y contenga máximo 3
     * decimales.
     *
     * @param precio Cadena que representa el valor monetario.
     * @return true si es un formato de precio válido, false en caso contrario.
     */
    public static boolean esPrecioValido(String precio) {
        if (precio == null || precio.trim().isEmpty()) {
            return false;
        }

        return precio.matches("^\\d+(\\.\\d{1,3})?$");
    }

    public static boolean esEnteroValido(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }
        return texto.matches("^\\d+$");
    }
}
