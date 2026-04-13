package utilerias;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Converter
/**
 * 
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class EncriptadorTelefono implements AttributeConverter<String, String> {

    private static final String ALGORITMO = "AES";
    private static final String LLAVE_SECRETA = "ProyectoBases123"; 

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