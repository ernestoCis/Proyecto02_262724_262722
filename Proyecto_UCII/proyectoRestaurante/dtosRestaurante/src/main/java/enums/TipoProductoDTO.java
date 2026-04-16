package enums;

/**
 * Define las categorías generales de los productos disponibles en el establecimiento.
 * <p>Este enumerado se utiliza para clasificar los artículos dentro del sistema, 
 * facilitando la organización del menú, la gestión de inventarios y la 
 * estructuración de los reportes de ventas.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public enum TipoProductoDTO {
    
    /** * Representa los alimentos principales que requieren preparación en cocina. 
     */
    PLATILLO,
    
    /** * Representa alimentos dulces servidos generalmente al final del servicio. 
     */
    POSTRE,
    
    /** * Representa todo tipo de líquidos y consumibles del área de barra o cafetería. 
     */
    BEBIDA
}