/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package enums;

/**
 * Define los posibles estados de una comanda dentro de la capa de transporte de datos.
 * <p>Este enumerado permite rastrear el ciclo de vida de un pedido desde su apertura
 * hasta su conclusión o anulación en el sistema.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public enum EstadoComandaDTO {
    
    /** * Indica que la comanda ha sido creada y se encuentra en proceso de recibir pedidos 
     * o en espera de ser servida. 
     */
    ABIERTA,
    
    /** * Indica que el servicio de la comanda ha concluido satisfactoriamente y los productos 
     * han sido servidos al cliente. 
     */
    ENTREGADA,
    
    /** * Indica que la comanda ha sido invalidada y no se procederá con el cobro ni el servicio. 
     */
    CANCELADA
}