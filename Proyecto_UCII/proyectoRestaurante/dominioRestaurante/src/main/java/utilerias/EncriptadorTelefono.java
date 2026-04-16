package utilerias;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Clase conversora para la encriptación y desencriptación de números telefónicos.
 * <p>Esta clase implementa {@link AttributeConverter} para asegurar que los datos sensibles 
 * (teléfonos) se almacenen cifrados en la base de datos y se recuperen en texto plano 
 * dentro de la aplicación.</p>
 * <p>Utiliza el algoritmo de cifrado simétrico <b>AES</b>.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
@Converter
public class EncriptadorTelefono implements AttributeConverter<String, String> {

    /** Algoritmo de cifrado utilizado: AES. */
    private static final String ALGORITMO = "AES";
    
    /** Llave secreta para el cifrado (debe tener 16 bytes para AES-128). */
    private static final String LLAVE_SECRETA = "ProyectoBases123"; 

    /**
     * Encripta el número telefónico antes de persistirlo en la base de datos.
     * <p>El resultado se codifica en <b>Base64</b> para asegurar la compatibilidad con 
     * columnas de texto en la BD.</p>
     * * @param atributo El número telefónico en texto plano.
     * @return El número telefónico cifrado y codificado en Base64.
     * @throws RuntimeException Si ocurre un error durante el proceso de cifrado.
     */
    @Override
    public String convertToDatabaseColumn(String atributo) {
        if (atributo == null) return null;
        try {
            SecretKeySpec secretKey = new SecretKeySpec(LLAVE_SECRETA.getBytes(), ALGORITMO);
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(atributo.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar: " + e.getMessage());
        }
    }

    /**
     * Desencripta el dato proveniente de la base de datos para su uso en la entidad.
     * <p>Decodifica el valor de <b>Base64</b> y posteriormente aplica el descifrado AES.</p>
     * * @param datosDb El dato cifrado almacenado en la base de datos.
     * @return El número telefónico original en texto plano.
     * @throws RuntimeException Si ocurre un error durante el proceso de descifrado.
     */
    @Override
    public String convertToEntityAttribute(String datosDb) {
        if (datosDb == null) return null;
        try {
            SecretKeySpec secretKey = new SecretKeySpec(LLAVE_SECRETA.getBytes(), ALGORITMO);
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(datosDb)));
        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar: " + e.getMessage());
        }
    }
}