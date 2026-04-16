package excepciones;

/**
 * 'PersistenciaException' es una excepción personalizada que utilizamos para 
 * envolver cualquier error técnico que ocurra al interactuar con la base de datos.
 * * Su propósito es ocultar la complejidad de JPA/SQL y entregar un mensaje 
 * más limpio y controlado a las capas superiores del sistema.
 * 
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class PersistenciaException extends Exception {

    /**
     * Crea una excepción con un mensaje descriptivo sobre el error ocurrido.
     * @param message
     */
    public PersistenciaException(String message) {
        super(message);
    }

    /**
     * Crea una excepción que, además del mensaje, conserva la causa original 
     * (el error técnico de JPA).Esto es vital para que el programador pueda 
 rastrear el problema real en los logs.
     * @param message
     * @param cause
     */
    public PersistenciaException(String message, Throwable cause) {
        super(message, cause);
    }
}