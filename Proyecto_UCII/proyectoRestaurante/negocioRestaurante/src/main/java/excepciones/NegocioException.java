/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Esta excepción se dispara cuando una regla de nuestra lógica de negocio se
 * rompe. Por ejemplo: intentar vender más productos de los que hay en stock.
 * 
 * @author Paulina Guevara, Ernesto Cisneros 
 */
public class NegocioException extends Exception {

    public NegocioException(String message) {
        super(message);
    }

    public NegocioException(String message, Throwable cause) {
        super(message, cause);
    }
}