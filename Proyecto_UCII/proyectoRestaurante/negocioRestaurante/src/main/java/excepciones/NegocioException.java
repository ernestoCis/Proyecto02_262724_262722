/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Representa una interrupción controlada en el flujo de la aplicación debido a
 * una violación de las reglas de negocio del sistema de restaurante. * Esta
 * excepción debe ser utilizada en la capa de Negocio (BO/Services) para validar
 * restricciones como:
 * <ul>
 * <li>Stock insuficiente para un pedido.</li>
 * <li>Intentar asignar una mesa que ya está ocupada.</li>
 * <li>Validaciones de formato de datos que no cumplen con los requisitos del
 * sistema.</li>
 * </ul>
 * * Al ser una excepción de tipo "Checked", garantiza que el programador
 * gestione el error, mejorando la robustez y la experiencia del usuario.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class NegocioException extends Exception {

    /**
     * Construye una nueva excepción con un mensaje descriptivo específico.
     *
     * * @param message El motivo por el cual la regla de negocio no pudo
     * cumplirse.
     */
    public NegocioException(String message) {
        super(message);
    }

    /**
     * Construye una nueva excepción con un mensaje descriptivo y la causa
     * original del error (encapsulamiento de excepciones).
     *
     * * @param message Descripción del error de negocio.
     * @param cause La excepción original (Throwable) que provocó el fallo.
     */
    public NegocioException(String message, Throwable cause) {
        super(message, cause);
    }
}
