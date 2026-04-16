package enums;

/**
 * Define los estados operativos en los que puede encontrarse una {@link entidades.Comanda}.
 * <p>Este enumerado permite controlar el ciclo de vida de un pedido, desde su 
 * creación hasta su finalización o anulación en el sistema.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public enum EstadoComanda {
    
    /** * Indica que la comanda ha sido creada y puede seguir recibiendo productos.
     * <p>El pedido aún está en proceso de preparación o atención.</p> 
     */
    ABIERTA,
    
    /** * Indica que el pedido ha sido servido al cliente y el ciclo de servicio 
     * ha concluido satisfactoriamente. 
     */
    ENTREGADA,
    
    /** * Indica que la comanda ha sido invalidada, ya sea por error en la orden 
     * o por solicitud del cliente, deteniendo cualquier proceso posterior. 
     */
    CANCELADA
}