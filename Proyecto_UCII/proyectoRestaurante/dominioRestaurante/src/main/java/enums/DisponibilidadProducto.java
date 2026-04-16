package enums;

/**
 * Define los estados de disponibilidad técnica de un producto en el sistema.
 * <p>Este enumerado se utiliza para determinar si un producto puede ser 
 * seleccionado para nuevas comandas o si debe ser ocultado del menú actual.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public enum DisponibilidadProducto {
    
    /** * Indica que el producto cuenta con insumos suficientes y está 
     * habilitado para su venta al público. 
     */
    DISPONIBLE,
    
    /** * Indica que el producto no puede ser solicitado, ya sea por falta de 
     * ingredientes o por decisión administrativa. 
     */
    NO_DISPONIBLE
}