package enums;

/**
 * Define los estados de disponibilidad comercial de un producto en la capa de transporte de datos.
 * <p>Este enumerado permite que la interfaz de usuario determine si un producto puede ser
 * agregado a un pedido o si debe mostrarse como agotado/fuera de carta.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public enum DisponibilidadProductoDTO {
    
    /** * Indica que el producto cuenta con existencias suficientes y puede ser solicitado. 
     */
    DISPONIBLE,
    
    /** * Indica que el producto no puede ser solicitado, ya sea por falta de insumos o por 
     * decisión administrativa. 
     */
    NO_DISPONIBLE
}